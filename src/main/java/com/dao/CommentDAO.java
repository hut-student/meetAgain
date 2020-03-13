package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.Comment;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDAO extends BaseMapper<Comment> {

    /**
     * 查找这条信息评论的最高楼层
     * @param sId 消息id
     * @return
     */
    @Select("select max(c_floor) from comment where s_id = #{sId}")
    public Integer selectMaxFloor(Integer sId);

    @Select("select count(c_id) from comment where s_id = #{sId}")
    public Integer selectCommentSum(Integer sId);
}
