package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.MeetAgainSpaceComment;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetAgainSpaceCommentDAO extends BaseMapper<MeetAgainSpaceComment> {

    @Select("select max(floor) from meet_again_space_comment where cf_id = #{cfId}")
    public Integer findManFloor(Integer cfId);
}
