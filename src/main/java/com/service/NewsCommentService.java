package com.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.NewsCommentDAO;
import com.pojo.NewsComment;
import com.utils.MyMiniUtils;
import com.utils.MyPage;
import com.vo.ResponseBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NewsCommentService extends ServiceImpl<NewsCommentDAO, NewsComment> {

    @Autowired
    private NewsCommentDAO newsCommentDAO;

    /**
     * 根据新闻id来找评论
     *利用数据库进行分页
     * @param newsId
     * @return
     */
    public ResponseBean selectFatherCommentByNewsId(String newsId, int page, int limit) {
        Page<NewsComment> p = new Page<NewsComment>(page, limit);
        IPage<NewsComment> iPage = newsCommentDAO.selectByNewId(newsId, p);
        return new ResponseBean(200, "ok", new MyPage<NewsComment>().iPageToMyPage(iPage));
    }


    /**
     * 增加一条评论
     * @param newsId
     * @param uId
     * @param commend
     * @return
     */
    public ResponseBean addOneCommet(String newsId, String uId, String commend) {
        NewsComment newsComment = new NewsComment(Integer.parseInt(uId), newsId, commend, LocalDateTime.now());
        synchronized (this) {
            Integer floor = newsCommentDAO.selectCommentMaxFloor(newsId);
            if (floor == null)
                newsComment.setXwcFloor(1);
            else
                newsComment.setXwcFloor(floor + 1);
            if (newsCommentDAO.insert(newsComment) == 1)
                return new ResponseBean(200, "评论成功", MyMiniUtils.getEncryptString(uId, System.currentTimeMillis()), null);
            else
                return new ResponseBean(201, "评论失败，请重试", null);
        }
    }

//分页
    public IPage<NewsComment> fenye() {
        IPage<NewsComment> page = new Page<>();

        return page;
    }


}
