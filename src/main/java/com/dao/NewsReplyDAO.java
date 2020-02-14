package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pojo.NewsReply;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsReplyDAO extends BaseMapper<NewsReply> {

    //评论id查回复
    public IPage<NewsReply> selectReplyByCommendId(Page p, int id);

    //找当前评论的最高层
    @Select("select Max(r_floor) from xw_reply where xwc_id = #{xwcId}")
    public Integer selectMaxReplyFloor(int xwcId);

}
