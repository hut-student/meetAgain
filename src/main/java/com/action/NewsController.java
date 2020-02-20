package com.action;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.service.NewsCommentService;
import com.service.NewsReplyService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsCommentService newsCommentService;

    @Autowired
    private NewsReplyService newsReplyService;

    /**
     * 新闻id来找评论
     * @param newsId 新闻id
     * @param page  第几页（从1开始）
     * @param limit 一页多少条   比如page=2&limit=4，那么将会显示评论的第九楼到第十二楼，共四条
     * @return
     */
    @RequestMapping("comment")
    public ResponseBean selectCommentByNewsId(String newsId, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "5") int limit){
        return newsCommentService.selectFatherCommentByNewsId(newsId, page, limit);
    }

    /**
     * 评论id找回复
     * @param commentId 评论id
     * @param page      第几页（从1开始）
     * @param limit     一页多少条   比如page=2&limit=4，那么将会显示评论的第九楼到第十二楼，共四条
     * @return
     */
    @RequestMapping("reply")
    public ResponseBean selectReplyByCommentId(int commentId, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "5") int limit){
        return newsReplyService.selectReplyByCommedId(commentId, page, limit);
    }

    /**
     * 添加新闻评论
     * @param newsId    新闻id
     * @param uId       用户id
     * @param comment   评论内容
     * @return
     */
    @RequestMapping("addComment")
    public ResponseBean addOneComment(String newsId, String uId, String comment){
        if(newsId != null && newsId.isEmpty() == false && uId != null && uId.isEmpty() == false && comment != null && comment.isEmpty() == false)
            return newsCommentService.addOneCommet(newsId,uId,comment);
        else
            return new ResponseBean(202,"缺少必要参数",null);
    }

    /**
     * 添加新闻评论的回复
     * @param xwcId     新闻评论的id
     * @param xwrFloor  回复的哪一层
     * @param fromUid   自己的id
     * @param toUid     目标对象的id
     * @param comment    回复的内容
     * @return
     */
    @RequestMapping("addReply")
    public ResponseBean addOneReply(int xwcId, int xwrFloor, int fromUid, int toUid, String comment){
        if(comment != null && comment.isEmpty() == false)
            return newsReplyService.addOneReply(xwcId, xwrFloor, fromUid, toUid, comment);
        else
            return new ResponseBean(202,"缺少必要参数",null);
    }

}
