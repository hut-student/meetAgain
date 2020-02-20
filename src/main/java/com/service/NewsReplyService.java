package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.NewsReplyDAO;
import com.dao.UserDAO;
import com.pojo.NewsReply;
import com.pojo.User;
import com.utils.MyMiniUtils;
import com.utils.MyPage;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class NewsReplyService extends ServiceImpl<NewsReplyDAO, NewsReply> {

    @Autowired
    private NewsReplyDAO newsReplyDAO;

    @Autowired
    private UserDAO userDAO;

    //评论id查回复
    public ResponseBean selectReplyByCommedId(int xwcId, int page, int limit) {
        //封装分页参数
        Page p = new Page(page, limit);
        IPage<NewsReply> replyList = newsReplyDAO.selectReplyByCommendId(p, xwcId);
        for (NewsReply reply : replyList.getRecords()) {
            reply.setToUname(userDAO.selectById(reply.getToUid()).getuName());
        }
        return new ResponseBean(200, "ok",new MyPage<NewsReply>().iPageToMyPage(replyList));
    }

    //添加一条回复
    public ResponseBean addOneReply(int xwcId, int xwrFloor, int fromUid, int toUid, String commed) {
        NewsReply newsReply = new NewsReply(xwcId, xwrFloor, fromUid, toUid, commed, LocalDateTime.now());
        int a;
        synchronized (this) {
            Integer floor = newsReplyDAO.selectMaxReplyFloor(xwcId);
            if (floor == null)
                newsReply.setrFloor(0);
            else
                newsReply.setrFloor(floor + 1);
            a = newsReplyDAO.insert(newsReply);
        }
            if (a == 1){
                QueryWrapper<NewsReply> queryWrapper = new QueryWrapper();
                queryWrapper.eq("xwc_id", newsReply.getXwcId());
                queryWrapper.eq("r_floor", newsReply.getrFloor());
                newsReply.setXwrId(newsReplyDAO.selectOne(queryWrapper).getXwrId());
                newsReply.setTime(MyMiniUtils.timeMillisChangeLocalDateTime(newsReply.getrTime()));
                User fromUser = userDAO.selectById(newsReply.getFromUid());
                newsReply.setFromUname(fromUser.getuName());
                newsReply.setuHeadPortrait(fromUser.getuHeadPortrait());
                newsReply.setToUname(userDAO.selectById(newsReply.getToUid()).getuName());
                return new ResponseBean(200, "评论成功", MyMiniUtils.getEncryptString(String.valueOf(fromUid), System.currentTimeMillis()), newsReply);
            }
            else
                return new ResponseBean(201, "评论失败，请重试", null);

    }


    //分页
//    public void fenye (){
//
//        newsReplyDAO.selectPage()
//    }

}
