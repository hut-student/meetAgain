package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pojo.NewsComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NewsCommentDAO extends BaseMapper<NewsComment> {

    //新闻id查评论
    public IPage selectByNewId(@Param("newsId") String newsId,Page page);

    //查找当前新闻评论的最高楼层
    @Select("select Max(xwc_floor) from xw_comments where xw_id=#{xwId}")
    public Integer selectCommentMaxFloor(String xwId);

}
