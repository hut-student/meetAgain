package com.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.NewsCommentDAO;
import com.dao.UserDAO;
import com.pojo.NewsComment;
import com.pojo.User;
import com.utils.MyMiniUtils;
import com.utils.MyPage;
import com.vo.ResponseBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NewsCommentService extends ServiceImpl<NewsCommentDAO, NewsComment> {

    @Autowired
    private NewsCommentDAO newsCommentDAO;

    @Autowired
    private UserDAO userDAO;

    @Value("${web.site}")
    private String webSite;

    @Value("${upload.headPortrait.dir}")
    private String headPhoto;

    /**
     * 根据新闻id来找评论
     *
     * @param newsId
     * @return
     */
    public ResponseBean selectFatherCommentByNewsId(String newsId, int page, int limit) {
        Page<NewsComment> p = new Page<NewsComment>(page, limit);
        IPage<NewsComment> iPage = newsCommentDAO.selectByNewId(newsId, p);
        for (NewsComment newsComment : iPage.getRecords()
        ) {
            newsComment.setReplySum(newsCommentDAO.selectReplySum(newsComment.getXwcId()));
            newsComment.setuHeadPortrait("https://" + webSite + headPhoto + newsComment.getuHeadPortrait());
            newsComment.setuName(userDAO.selectById(newsComment.getuId()).getuName());
        }
        return new ResponseBean(200, "ok", new MyPage<NewsComment>().iPageToMyPage(iPage));
    }


    /**
     * 增加一条评论
     *
     * @param newsId
     * @param uId
     * @param comment
     * @return
     */
    public ResponseBean addOneCommet(String newsId, String uId, String comment) {
        NewsComment newsComment = new NewsComment(Integer.parseInt(uId), newsId, comment, LocalDateTime.now());
        int lock;
        synchronized (this) {
            Integer floor = newsCommentDAO.selectCommentMaxFloor(newsId);
            if (floor == null)
                newsComment.setXwcFloor(1);
            else
                newsComment.setXwcFloor(floor + 1);
            lock = newsCommentDAO.insert(newsComment);
        }
            if (lock == 1) {
                QueryWrapper<NewsComment> queryWrapper = new QueryWrapper();
                queryWrapper.eq("xw_id", newsComment.getXwId()).eq("xwc_floor", newsComment.getXwcFloor());
                newsComment.setXwcId(newsCommentDAO.selectOne(queryWrapper).getXwcId());
                User user = userDAO.selectById(newsComment.getuId());
                newsComment.setuName(user.getuName());
                newsComment.setuHeadPortrait("https://" + webSite + headPhoto + user.getuHeadPortrait());
                return new ResponseBean(200, "评论成功", MyMiniUtils.getEncryptString(uId, System.currentTimeMillis()), newsComment);
            } else
                return new ResponseBean(201, "评论失败，请重试", null);
    }


}
