package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.CommentDAO;
import com.dao.ReplyDAO;
import com.dao.UserDAO;
import com.pojo.*;
import com.tencentcloudapi.emr.v20190103.models.NewResourceSpec;
import com.utils.MyMiniUtils;
import com.utils.MyPage;
import com.vo.ResponseBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchCommentService {

    private static Integer replyLock = new Integer(1);

    @Autowired
    private MyMiniUtils myMiniUtils;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ReplyDAO replyDAO;


    //添加一条评论
    public ResponseBean addSearchComment(Integer uId, Integer sId, String cComment) {
        Comment comment = new Comment(uId, sId, cComment, LocalDateTime.now());
        boolean check; //用来判断是否插入成功
        Integer maxFloor;
        synchronized (this) {
            maxFloor = commentDAO.selectMaxFloor(sId);
            if (maxFloor != null) {
                maxFloor++;
            } else {
                maxFloor = 1;
            }
            comment.setcFloor(maxFloor);
            if (commentDAO.insert(comment) == 1) {
                check = true;
            } else {
                check = false;
            }
        }
        if (check) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("u_id", comment.getuId());
            queryWrapper.eq("c_time", comment.getcTime());
            comment = commentDAO.selectOne(queryWrapper);
            User user = userDAO.selectById(comment.getuId());
            NewsComment newsComment = myMiniUtils.searchCommentToNewsComment(user, comment, 0);
            System.out.println(newsComment);
            return new ResponseBean(200, "评论成功", newsComment);
        } else {
            return new ResponseBean(403, "添加失败", null);
        }
    }

    //添加一条回复
    public ResponseBean addOneReply(Integer cId, Integer toFloor, Integer fromUid, Integer toUid, String comment) {
        if(cId == null || toFloor == null || fromUid == null || toUid == null || comment == null || comment.length() == 0){
            return new ResponseBean(403,"缺少参数", null);
        }
        Reply reply = new Reply(cId, toFloor, fromUid, toUid, comment, LocalDateTime.now());
        Integer maxFloor;
        boolean replyCheck;
        synchronized (replyLock) {
            maxFloor = replyDAO.selectMaxFloor(cId);
            if (maxFloor == null) {
                maxFloor = 1;
            } else {
                maxFloor++;
            }
            reply.setrFloor(maxFloor);
            if (replyDAO.insert(reply) == 1) {
                replyCheck = true;
            } else {
                replyCheck = false;
            }
        }
        if (replyCheck) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("from_uid", reply.getFromUid());
            queryWrapper.eq("r_time", reply.getrTime());
            reply = replyDAO.selectOne(queryWrapper);
            User fromUser = userDAO.selectById(reply.getFromUid());
            User toUser = userDAO.selectById(reply.getToUid());
            NewsReply newsReply = myMiniUtils.searchReplyToNewsReply(reply, fromUser, toUser);
            System.out.println(newsReply);
            return new ResponseBean(200, "添加成功", newsReply);
        } else {
            return new ResponseBean(201, "评论失败", null);
        }
    }

    //寻找评论
    public ResponseBean findSearchComment(Integer s_id, Integer page, Integer limit) {
        try {
            Page<Comment> p = new Page<>(page, limit);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("s_id", s_id);
            IPage<Comment> iPage = commentDAO.selectPage(p, queryWrapper);
            int size;
            List<NewsComment> newsComments = new ArrayList<>();
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("s_id", s_id);
            Integer commentSum = commentDAO.selectCount(queryWrapper);
            for (size = 0; size < iPage.getRecords().size(); size++) {
                QueryWrapper queryWrapper1 = new QueryWrapper();
                queryWrapper.eq("c_id", iPage.getRecords().get(size).getcId());
                queryWrapper.orderByAsc("c_floor");
                //将ipge中的comment改为newsComments，然后装入newsComments的集合中
                newsComments.add(myMiniUtils.searchCommentToNewsComment(userDAO.selectById(iPage.getRecords().get(size).getuId()), iPage.getRecords().get(size), replyDAO.selectCount(queryWrapper1), commentSum));
            }
            MyPage myPage = new MyPage().iPageToMyPage(iPage);
            myPage.setRecords(newsComments);
            return new ResponseBean(200,"查询成功",myPage);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(404,"服务器异常", null);
        }
    }

    //寻找回复
    public ResponseBean findReply(Integer cId, int page, int limit){
        try {
            Page<Reply> p = new Page<>(page, limit);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("c_id", cId);
            queryWrapper.orderByAsc("r_floor");
            IPage<Reply> iPage = replyDAO.selectPage(p, queryWrapper);
            List<NewsReply> newsReplies = new ArrayList<>();
            for(Reply reply : iPage.getRecords()){
                User fromUser = userDAO.selectById(reply.getFromUid());
                User toUser = userDAO.selectById(reply.getToUid());
                newsReplies.add(myMiniUtils.searchReplyToNewsReply(reply, fromUser, toUser));
            }
            MyPage myPage = new MyPage().iPageToMyPage(iPage);
            myPage.setRecords(newsReplies);
            return new ResponseBean(200,"查询成功", myPage);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(404,"服务器异常",null);
        }
    }




}
