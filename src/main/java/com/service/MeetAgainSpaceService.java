package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.MeetAgainSpaceCommentDAO;
import com.dao.MeetAgainSpaceDAO;
import com.dao.UserDAO;
import com.dao.UserRelationshipDAO;
import com.pojo.MeetAgainSpace;
import com.pojo.MeetAgainSpaceComment;
import com.pojo.User;
import com.tencentcloudapi.vpc.v20170312.models.Ip6Rule;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetAgainSpaceService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MeetAgainSpaceDAO meetAgainSpaceDAO;

    @Autowired
    private MeetAgainSpaceCommentDAO meetAgainSpaceCommentDAO;

    @Autowired
    private UserRelationshipDAO userRelationshipDAO;

    @Value("${upload.headPortrait.dir}")
    private String headPhoto;

    @Value("${upload.meetAgainSpace.dir}")
    private String meetAgainSpaceDir;

    @Value("${web.site}")
    private String webSiteDir;

    //填充重逢圈内容
    private MeetAgainSpace fillMASpace(MeetAgainSpace meetAgainSpace) {
        //填充用户信息
        User user = userDAO.selectById(meetAgainSpace.getuId());
        meetAgainSpace.setNickName(user.getuName());
        meetAgainSpace.setHeader("https://" + webSiteDir + headPhoto + user.getuHeadPortrait());

        //填充图片
        List<String> photos = JSON.parseArray(meetAgainSpace.getCfPhoto(), String.class);
        List<String> images = new ArrayList<>();
        for (String photo : photos) {
            images.add("https://" + webSiteDir + meetAgainSpaceDir + photo);
        }
        meetAgainSpace.setImages(images);

        //填充评论数
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cf_id", meetAgainSpace.getCommunityId());
        Integer commentNum = meetAgainSpaceCommentDAO.selectCount(queryWrapper);
        meetAgainSpace.setCommentNum(commentNum);
        //如果有评论，则显示前3条
        if (commentNum != null && commentNum != 0) {
            queryWrapper.last("limit 3");
            meetAgainSpace.setComments(meetAgainSpaceCommentDAO.selectList(queryWrapper));
        }
        return meetAgainSpace;
    }

    //发布重逢圈
    public ResponseBean addMeetAgainSpace(String uId, String locate, String content, Integer file, HttpServletRequest request) {
        try {
            MeetAgainSpace meetAgainSpace = new MeetAgainSpace();
            if (locate != null) {
                meetAgainSpace.setLocate(locate);
            }
            meetAgainSpace.setuId(Integer.valueOf(uId));
            meetAgainSpace.setContent(content);
            meetAgainSpace.setTime(System.currentTimeMillis());
            List<String> fileName = new ArrayList<>();
            List<String> fileDir = new ArrayList<>();
            if (file != null && file > 0) {
                //保存图片
                MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
                int i;
                for (i = 0; i < file; i++) {
                    MultipartFile multipartFile = params.getFile("file" + i);
                    String name = MyMiniUtils.upLoadFile(multipartFile, meetAgainSpaceDir);
                    if (name != null) {
                        fileName.add(name);
                        fileDir.add("https://" + webSiteDir + meetAgainSpaceDir + name);
                    }
                }
            }
            meetAgainSpace.setImages(fileDir);
            meetAgainSpace.setCfPhoto(JSON.toJSONString(fileName));
            meetAgainSpaceDAO.insert(meetAgainSpace);
            //获得用户信息
            User user = userDAO.selectById(uId);
            meetAgainSpace.setHeader("https://" + webSiteDir + headPhoto + user.getuHeadPortrait());
            meetAgainSpace.setNickName(user.getuName());
            //获得评论总数
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("cf_id", meetAgainSpace.getCommunityId());
            meetAgainSpace.setCommentNum(meetAgainSpaceCommentDAO.selectCount(queryWrapper));
            return new ResponseBean(200, "成功", meetAgainSpace);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400, e.getMessage(), null);
        }
    }

    //添加评论
    public ResponseBean addComment(String cfId, String uId, String toUid, String comment) {
        try {
            MeetAgainSpaceComment meetAgainSpaceComment = new MeetAgainSpaceComment();
            meetAgainSpaceComment.setCommunityId(Integer.valueOf(cfId));
            if (toUid != null) {
                meetAgainSpaceComment.setToUId(Integer.valueOf(toUid));
            }
            meetAgainSpaceComment.setuId(Integer.valueOf(uId));
            meetAgainSpaceComment.setTime(System.currentTimeMillis());
            meetAgainSpaceComment.setComment(comment);
            //获得之前的楼层
            synchronized (this) {
                Integer floor = meetAgainSpaceCommentDAO.findManFloor(Integer.valueOf(cfId));
                if (floor != null) {
                    meetAgainSpaceComment.setFloor(floor + 1);
                } else {
                    meetAgainSpaceComment.setFloor(0);
                }
            }
            meetAgainSpaceCommentDAO.insert(meetAgainSpaceComment);
            //添加用户信息
            User fromUser = userDAO.selectById(meetAgainSpaceComment.getuId());
            meetAgainSpaceComment.setHeader("https://" + webSiteDir + headPhoto + fromUser.getuHeadPortrait());
            meetAgainSpaceComment.setNickName(fromUser.getuName());
            //添加toUser的信息
            if (toUid != null) {
                User toUser = userDAO.selectById(meetAgainSpaceComment.getToUId());
                meetAgainSpaceComment.setToName(toUser.getuName());
            }
            return new ResponseBean(200, "评论成功", meetAgainSpaceComment);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400, e.getMessage(), null);
        }
    }

    //获得关注人的重逢圈
    public ResponseBean findRelationshipMASpace(String uId, Integer page) {
        try {
            List<Integer> friends = userRelationshipDAO.selectUserFriends(Integer.valueOf(uId));
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.in("u_id", friends);
            queryWrapper.orderByDesc("cf_id");
            Page p = new Page(page, 10);
            IPage<MeetAgainSpace> meetAgainSpaceIPage = meetAgainSpaceDAO.selectPage(p, queryWrapper);
            List<MeetAgainSpace> meetAgainSpaceList = new ArrayList<>();
            if (meetAgainSpaceIPage.getRecords() != null && meetAgainSpaceIPage.getRecords().size() > 0) {
                for (MeetAgainSpace meetAgainSpace : meetAgainSpaceIPage.getRecords()) {
                    //填充重逢圈信息
                    meetAgainSpaceList.add(fillMASpace(meetAgainSpace));
                }
                return new ResponseBean(200, "查询完成", meetAgainSpaceList);
            } else {
                return new ResponseBean(300, "list为空", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400, e.getMessage(), null);
        }
    }

    //按热度获取重逢圈
    public ResponseBean findMASpace(Integer page) {
        try {
            Page<MeetAgainSpace> p = new Page<>(page, 10);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.orderByDesc("cf_id");
            List<MeetAgainSpace> meetAgainSpaceList = meetAgainSpaceDAO.selectPage(p, queryWrapper).getRecords();
            List<MeetAgainSpace> meetAgainSpaces = new ArrayList<>();
            if (meetAgainSpaceList != null && meetAgainSpaceList.size() > 0) {
                for (MeetAgainSpace meetAgainSpace : meetAgainSpaceList) {
                    meetAgainSpaces.add(fillMASpace(meetAgainSpace));
                }
                return new ResponseBean(200, "成功", meetAgainSpaces);
            } else {
                return new ResponseBean(300, "List为空", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400, e.getMessage(), null);
        }
    }

    //获取重逢圈 某个用户的发布内容
    public ResponseBean findOneMASpace(String uId) {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("u_id", Integer.valueOf(uId));
            List<MeetAgainSpace> meetAgainSpaceList = meetAgainSpaceDAO.selectList(queryWrapper);
            List<MeetAgainSpace> meetAgainSpaces = new ArrayList<>();
            if (meetAgainSpaceList != null && meetAgainSpaceList.size() > 0) {
                for (MeetAgainSpace meetAgainSpace : meetAgainSpaceList) {
                    meetAgainSpaces.add(fillMASpace(meetAgainSpace));
                }
                return new ResponseBean(200, "成功", meetAgainSpaces);
            } else {
                return new ResponseBean(300, "List为空", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400, e.getMessage(), null);
        }
    }

    /**
     * 获取重逢圈 获取评论内容
     * @param communityId
     * @return
     */
    public ResponseBean findComment(String communityId, Integer page){
        try {
            if(meetAgainSpaceDAO.selectById(Integer.valueOf(communityId)) == null){
                return new ResponseBean(400,"参数错误", null);
            }
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("cf_id", Integer.valueOf(communityId));
            Page<MeetAgainSpaceComment> p = new Page<>(page, 5);
            List<MeetAgainSpaceComment> meetAgainSpaceCommentList = meetAgainSpaceCommentDAO.selectPage(p, queryWrapper).getRecords();
            if(meetAgainSpaceCommentList != null && meetAgainSpaceCommentList.size() > 0){
                for(MeetAgainSpaceComment meetAgainSpaceComment : meetAgainSpaceCommentList){
                    //进行评论填充
                    User fromUser = userDAO.selectById(meetAgainSpaceComment.getuId());
                    meetAgainSpaceComment.setHeader("https://" + webSiteDir + headPhoto + fromUser.getuHeadPortrait());
                    meetAgainSpaceComment.setNickName(fromUser.getuName());
                    if(meetAgainSpaceComment.getToUId() != null){
                        User toUser = userDAO.selectById(meetAgainSpaceComment.getToUId());
                        meetAgainSpaceComment.setToName(toUser.getuName());
                    }
                }
                return new ResponseBean(200,"查找成功", meetAgainSpaceCommentList);
            }else {
                return new ResponseBean(300,"list为空", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400,e.getMessage(),null);
        }
    }
}
