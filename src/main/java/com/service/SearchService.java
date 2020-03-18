package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.CommentDAO;
import com.dao.SearchDAO;
import com.dao.UserDAO;
import com.dao.UserStarDAO;
import com.google.gson.annotations.JsonAdapter;
import com.pojo.Comment;
import com.pojo.Search;
import com.pojo.User;
import com.pojo.UserStar;
import com.utils.MyMiniUtils;
import com.utils.MyPage;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService extends ServiceImpl<SearchDAO, Search> {

    @Autowired
    private SearchDAO searchDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private UserStarDAO userStarDAO;

    //用户头像
    @Value("${upload.headPortrait.dir}")
    private String headPortraitDir;

    //寻人图片文件夹
    @Value("${upload.findPeople.dir}")
    private String findPeopleDir;

    //网站域名
    @Value("${web.site}")
    private String webSite;


    //添加一条记录
    public ResponseBean addSearch(String searchJson, HttpServletRequest request) {
        Search search = JSON.parseObject(searchJson, Search.class);
        search.setsTime(LocalDateTime.now());
        //将图片保存到服务器和数据库
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = params.getFiles("file");
        List<String> imgs = new ArrayList<String>();
        String newName;
        for (MultipartFile file : files
        ) {
            newName = MyMiniUtils.upLoadFile(file, findPeopleDir);
            if (newName == null) {
                return new ResponseBean(201, "图片上传失败,请重试", null);
            }
            imgs.add(newName);
        }
        //将图片的json格式添加进photo段
        search.setsPhoto(JSON.toJSONString(imgs));
        if (searchDAO.insert(search) == 1) {
            //以添加事件
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("u_id", search.getuId());
            queryWrapper.eq("s_photo", search.getsPhoto());
            search = searchDAO.selectOne(queryWrapper);
            List<String> list = JSON.parseArray(search.getsPhoto(), String.class);
            List<String> newList = new ArrayList<>();
            for (String dir : list) {
                newList.add("https://" + webSite + findPeopleDir + dir);//寻人图片发送
            }
            search.setPictures(newList);
            User user = userDAO.selectById(search.getuId());
            search.setHeader("https://" + webSite + headPortraitDir + user.getuHeadPortrait());
            search.setNickName(user.getuName());
            System.out.println(search);
            return new ResponseBean(200, "添加成功", search);
        }
        return new ResponseBean(201, "添加失败", null);

    }

    //修改记录
    public ResponseBean updateSearch(String SearchJson) {
        Search search = JSON.parseObject(SearchJson, Search.class);
        if (searchDAO.updateById(search) == 1) {
            return new ResponseBean(200, "修改成功", searchDAO.selectById(search));
        } else {
            return new ResponseBean(201, "修改失败", null);
        }
    }

    //获取用户自己的历史发布信息
    public ResponseBean findUserSearch(Integer type, Integer uId, Integer page, Integer limit) {
        try {
            Page<Search> p = new Page(page, limit);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("u_id", uId);
            queryWrapper.eq("s_type", type);
            queryWrapper.orderByDesc("s_time");
            IPage<Search> ipage = searchDAO.selectPage(p, queryWrapper);
            if(ipage.getRecords() == null || ipage.getRecords().size() == 0){
                return new ResponseBean(300,"未查到结果",null);
            }
            User user = userDAO.selectById(uId);
            List<String> photoDir = new ArrayList<>();
            for (int i = 0; i < ipage.getRecords().size(); i++) {
                ipage.getRecords().get(i).setPictures(JSON.parseArray(ipage.getRecords().get(i).getsPhoto(), String.class)); //图片
                for(String dir : ipage.getRecords().get(i).getPictures()){
                    photoDir.add("https://" + webSite + findPeopleDir + dir);
                }
                ipage.getRecords().get(i).setPictures(photoDir);
                ipage.getRecords().get(i).setNickName(user.getuName());
                ipage.getRecords().get(i).setHeader("https://" + webSite + headPortraitDir + user.getuHeadPortrait());
                //还得设置评论数
                ipage.getRecords().get(i).setCommentNum(commentDAO.selectCommentSum(ipage.getRecords().get(i).getsId()));
            }
            return new ResponseBean(200, "查询成功", ipage.getRecords());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseBean(400,"uid不存在",null);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(403,"服务器异常",null);
        }
    }

    /**
     * 改变寻人寻物收藏
     * @param uId
     * @param sId
     * @param star
     * @return
     */
    public ResponseBean changeStar(Integer uId, Integer sId, Integer star) throws Exception{
        if(userDAO.selectById(uId) == null){
            return new ResponseBean(401,"该用户不存在",null);
        }
        if(searchDAO.selectById(sId) == null){
            return new ResponseBean(402,"该帖子不存在", null);
        }
        QueryWrapper<UserStar> queryWrapper = new QueryWrapper();
        queryWrapper.eq("u_id", uId);
        queryWrapper.eq("s_id", sId);
        if(star == 0){
            //收藏该帖子
            if(userStarDAO.selectOne(queryWrapper) != null){
                //表示之前已经收藏了该帖子
                return  new ResponseBean(200, "已收藏该帖子", null);
            }else {
                //之前未收藏该帖子
                UserStar userStar = new UserStar();
                userStar.setuId(uId);
                userStar.setsId(sId);
                userStar.setStar(0);
                if(userStarDAO.insert(userStar) == 1){
                    //收藏成功
                    return new ResponseBean(200,"成功收藏", null);
                }else {
                    //收藏失败
                    return new ResponseBean(400,"收藏失败", null);
                }
            }
        }else if(star == 1){
            //取消收藏该帖子
            if(userStarDAO.selectOne(queryWrapper) == null){
                //表示数据库已经删去了这个记录
                return new ResponseBean(200,"已经取消收藏了", null);
            }else {
                //还未删除这条记录
                if(userStarDAO.delete(queryWrapper) == 1){
                    //删除成功
                    return new ResponseBean(200, "取消收藏成功", null);
                }else {
                    return new ResponseBean(400,"取消收藏失败", null);
                }
            }
        }
        return new ResponseBean(403,"收藏参数错误", null);
    }


    //根据关键词对寻人/寻物的帖子进行模糊搜索
    public ResponseBean keyWordFindSearch(String keyWord, Integer page){
        try {
            //如果这个是search的id号
            int sId;
            ArrayList<Search> searchArrayList = new ArrayList<>();
            try {
                sId = Integer.valueOf(keyWord);
                Search search = searchDAO.selectById(sId);
                if(search != null && page == 1){
                    searchArrayList.add(search);
                    return new ResponseBean(200, "查询成功", searchArrayList);
                }else {
                    return new ResponseBean(300,"List为空，找不到了",null);
                }
            } catch (Exception e) {
                System.out.println("不是Search的id");
            }
            //如果是其他的字符串（标题、省份、城市、区、街道）
            QueryWrapper<Search> queryWrapper = new QueryWrapper();
            queryWrapper.like(true,"s_title", keyWord).or();
            queryWrapper.like(true,"s_province", keyWord).or();
            queryWrapper.like(true, "s_city", keyWord).or();
            queryWrapper.like(true, "s_district", keyWord).or();
            queryWrapper.like(true, "s_address", keyWord);
            queryWrapper.orderByDesc("s_time");
            Page<Search> p = new Page<>(page, 10);
            IPage<Search> iPage = searchDAO.selectPage(p, queryWrapper);
            if(iPage.getRecords() != null && iPage.getRecords().size() > 0){
                return new ResponseBean(200,"查询成功", iPage.getRecords());
            }else {
                return new ResponseBean(300,"List为空", null);
            }
        } catch (Exception e) {
            return new ResponseBean(400,e.getMessage(), null);
        }
    }

    //对用户自己发表的寻人/寻物帖子进行删除
    public ResponseBean deleteSearch(Integer uId, Integer sId){
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("u_id", uId);
            queryWrapper.eq("s_id", sId);
            if(searchDAO.selectOne(queryWrapper) != null){
                if(searchDAO.delete(queryWrapper) == 1){
                    //删除成功
                    return new ResponseBean(200,"删除成功", null);
                }else {
                    return new ResponseBean(400,"数据库异常", null);
                }
            }else {
                //未找到这条记录
                return new ResponseBean(200,"数据库未找到", null);
            }
        } catch (Exception e) {
            return new ResponseBean(400,e.getMessage(),null);
        }
    }
}
