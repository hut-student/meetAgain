package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.*;
import com.google.gson.annotations.JsonAdapter;
import com.pojo.ApplicationFriend;
import com.pojo.Search;
import com.pojo.User;
import com.pojo.UserRelationship;
import com.tencentcloudapi.vpc.v20170312.models.Ip6Rule;
import com.utils.MyMiniUtils;
import com.utils.MyPage;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.ar.CalendarData_ar_YE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

@Service
public class UserRelationshipService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SearchDAO searchDAO;

    @Autowired
    private UserRelationshipDAO userRelationshipDAO;

    @Autowired
    private ApplicationFriendDAO applicationFriendDAO;

    @Value("${upload.findPeople.dir}")
    private String findPeople;

    @Value("${web.site}")
    private String webSite;

    @Value("${upload.headPortrait.dir}")
    private String headPhoto;


/*    //用户申请添加好友
    public ResponseBean applyFriend(Integer fromUid, Long time, String enCode, Integer toUid, String validationMsg) {
        ResponseBean responseBean = userService.userCheck(fromUid.toString(), time, enCode);
        if (responseBean.getCode() != 000) {
            return responseBean;
        }
        ApplicationFriend applicationFriend = null;
        if (validationMsg == null) {
            applicationFriend = new ApplicationFriend(fromUid, toUid, validationMsg, LocalDateTime.now());
        } else {
            applicationFriend = new ApplicationFriend(fromUid, toUid, LocalDateTime.now());
        }
        if(applicationFriendDAO.insert(applicationFriend) == 1){
            return new ResponseBean(200,"申请成功",null);
        }else {
            return new ResponseBean(404,"申请失败",null);
        }
    }*/

/*    //用户同意添加好友
    public ResponseBean agreeFriend(Integer toUid, Long time, String enCode, Integer fromUid){
        ResponseBean responseBean = userService.userCheck(toUid.toString(), time, enCode);
        if(responseBean.getCode() != 000){
            return responseBean;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("to_uid",toUid);
        queryWrapper.eq("from_uid",fromUid);
        if(applicationFriendDAO.selectOne(queryWrapper) != null){
            applicationFriendDAO.delete(queryWrapper);
            UserRelationship userRelationship = new UserRelationship(fromUid, toUid);
            if(userRelationshipDAO.insert(userRelationship) == 1 ){
                return new ResponseBean(200,"好友添加成功",null);
            }else {
                return new ResponseBean(403,"系统异常",null);
            }
        }else {
            return new ResponseBean(404,"用户提交数据异常",null);
        }
    }*/

    //根据用户的uid获取他关注的人发表的说说，以发布时间为顺序，内容混合寻人和寻物
    public ResponseBean findFriendsSearch(Integer uId, Integer page, Integer limit) {
        try {
            if (userDAO.selectById(uId) == null) {
                return new ResponseBean(400, "用户id不存在", null);
            }
            Page p = new Page(page, limit);
            //获取这个用户的所有关注的人
            List<Integer> userRelationships = userRelationshipDAO.selectUserFriends(uId);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.in("u_id", userRelationships); //将关注的人条件插入到构造器中
            queryWrapper.orderByDesc("s_time");
            IPage<Search> iPage = userRelationshipDAO.selectPage(p, queryWrapper);
            if (iPage.getRecords() == null || iPage.getRecords().size() == 0) {
                return new ResponseBean(300, "结果为空", null);
            }
            return new ResponseBean(200, "查找成功", iPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(500, "服务器错误", null);
        }
    }

    //根据热度来查找寻人寻物
    public ResponseBean findHotSearch(Integer page, Integer limit) {
        try {
            Page<Search> p = new Page<>(page, limit);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.orderByDesc("s_page_view", "s_time");
            IPage<Search> iPage = searchDAO.selectPage(p, queryWrapper);
            if (iPage.getRecords() == null || iPage.getRecords().size() == 0) {
                return new ResponseBean(300, "结果为空", null);
            }
            List<Search> searches = iPage.getRecords();
            User user = null;
            List<String> newPictures = new ArrayList<>();
            for (Search search : searches) {
                user = userDAO.selectById(search.getuId());
                search.setHeader("https://" + webSite + headPhoto + user.getuHeadPortrait());
                search.setNickName(user.getuName());
                search.setCommentNum(commentDAO.selectCommentSum(search.getsId()));
                search.setPictures(JSON.parseArray(search.getsPhoto(), String.class));
                for (String dir : search.getPictures()) {
                    newPictures.add("https://" + webSite + findPeople + dir);
                }
                search.setPictures(newPictures);
            }
            return new ResponseBean(200, "查找成功", searches);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(500, "服务器错误", null);
        }
    }

    //按照用户当前定位获取说说，内容混合寻人和寻物
    public ResponseBean findDirSearch(String locate, Integer page, Integer limit) {
        //进行经纬度的判断
        int index = locate.indexOf(",");
        Double lng = Double.valueOf(locate.substring(0, index)); //获得经度
        Double lat = Double.valueOf(locate.substring(index + 1)); //获得纬度
        int firstI = 0; //从那条记录读取
        List<Search> finalSearchs = new ArrayList<>(); //符合条件的添加进来
        int i = 0; //数量标记位
        boolean all = false;
        while (true) {
            List<Search> searches = findSearchLimit(firstI);
            for (Search search : searches) {
                if (checkDistance(search, lng, lat) == true) {
                    i++;
                    System.out.println(i);
                    if (i > (page - 1) * limit && i <= (page) * limit)
                        finalSearchs.add(search);
                }
            }
            if (finalSearchs.size() >= limit) {
                break;
            }
            if(searches.size() == 0 && finalSearchs.size() > 0){
                break;
            }
            if (searches.size() == 0) {
                all = true;
                break;
            }
            firstI = firstI + 50;
        }
        List<Search> abcSearch = new ArrayList<>();
        for(Search search : finalSearchs){
            /*User user = userDAO.selectById(search.getuId());
            search.setPictures(JSON.parseArray(search.getsPhoto(), String.class));
            for(String dir : search.getPictures()){
                newPhotoDir.add("https://" + webSite + findPeople + dir);
            }
            search.setPictures(newPhotoDir);
            search.setNickName(user.getuName());
            search.setHeader("https://" + webSite + headPhoto + user.getuHeadPortrait());
            search.setCommentNum(commentDAO.selectCommentSum(search.getsId()));*/
            abcSearch.add(searchFill(search));
        }
        if (all) {
            return new ResponseBean(300, "结果为空", null);
        } else {
            return new ResponseBean(200, "查询成功", abcSearch);
        }
    }

    /**
     * 为上面方法服务
     *
     * @param begin
     * @return
     */
    private List<Search> findSearchLimit(Integer begin) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("s_time");
        queryWrapper.last("limit " + begin + "," + 50);
        return searchDAO.selectList(queryWrapper);
    }


    /**
     * 算出这条记录是否符合要求
     *
     * @param search
     * @param lng
     * @param lat
     * @return
     */
    public boolean checkDistance(Search search, Double lng, Double lat) {
        try {
            if (search.getsJW1() == null || search.getsJW1().length() == 0) {
                return false;
            }
            String one = search.getsJW1();
            String two = search.getsJW2();
            String three = search.getsJW3();
            int index = one.indexOf(",");
            double lng1 = Double.valueOf(one.substring(0, index));
            double lat1 = Double.valueOf(one.substring(index + 1));
            if (MyMiniUtils.getWGS84Distance(lng, lat, lng1, lat1) < 20000) {
                return true;
            }
            index = two.indexOf(",");
            lng1 = Double.valueOf(two.substring(0, index));
            lat1 = Double.valueOf(two.substring(index + 1));
            if (MyMiniUtils.getWGS84Distance(lng, lat, lng1, lat1) < 20000) {
                return true;
            }
            index = three.indexOf(",");
            lng1 = Double.valueOf(three.substring(0, index));
            lat1 = Double.valueOf(three.substring(index + 1));
            if (MyMiniUtils.getWGS84Distance(lng, lat, lng1, lat1) < 20000) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //按照发布时间顺序和条件获取寻人说说
    public ResponseBean findSearchPeople(String time, String age, String province, String city, String district, Integer page) {
        try {
            Integer ageBegin;
            Integer ageEnding;
            QueryWrapper queryWrapper = new QueryWrapper();
            if(age == null){
                queryWrapper.eq("s_type", 1);
            }else {
                queryWrapper.eq("s_type", 0);
            }

            if (age != null && !age.equals("abc")) {
                System.out.println(age);
                int index = age.indexOf("-");
                System.out.println(index);
                ageBegin = Integer.valueOf(age.substring(0, index));
                ageEnding = Integer.valueOf(age.substring(index + 1));
                queryWrapper.between("s_age", ageBegin, ageEnding);
            }
            if (!time.equals("abc")) {
                time = time + "T00:00";
                String lTime = time + "T23:59";
                LocalDateTime firstTime = LocalDateTime.parse(time);
                LocalDateTime lastTime = LocalDateTime.parse(lTime);
                queryWrapper.between("s_lost_time", firstTime, lastTime);
            }
            if (!province.equals("abc")) {
                queryWrapper.eq("s_province", province);
                queryWrapper.eq("s_city", city);
                queryWrapper.eq("s_district", district);
            }
            queryWrapper.orderByDesc("s_time");
            int firstI = (page - 1) * 10;
            queryWrapper.last("limit " + firstI + ",10");
            List<Search> newSearches = searchDAO.selectList(queryWrapper);
            List<Search> finalSearchs = new ArrayList<>();
            for(Search search : newSearches){
                finalSearchs.add(searchFill(search));
            }
            if (newSearches.size() == 0) {
                return new ResponseBean(300, "结果为空", null);
            } else {
                return new ResponseBean(200, "搜索成功", finalSearchs);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ResponseBean(500, "服务器异常", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(500, "服务器异常", null);
        }
    }


    //填充search对象
    public Search searchFill(Search search){
        User user = userDAO.selectById(search.getuId());
        search.setNickName(user.getuName());
        search.setCommentNum(commentDAO.selectCommentSum(search.getsId()));
        search.setPictures(JSON.parseArray(search.getsPhoto(), String.class));
        List<String> newsPhotos = new ArrayList<>();
        if(search.getPictures() != null){
            for(String dir : search.getPictures()){
                newsPhotos.add("https://" + webSite + findPeople + dir);
            }
            search.setPictures(newsPhotos);
        }
        search.setHeader("https://" + webSite + headPhoto + search.getHeader());
        return search;
    }
}
