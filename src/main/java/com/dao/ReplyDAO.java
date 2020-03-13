package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.Reply;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyDAO extends BaseMapper<Reply> {

    @Select("select max(r_floor) from reply where c_id = #{cId}")
    public Integer selectMaxFloor(Integer cId);
}
