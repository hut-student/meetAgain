package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.GroupInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupInfoDAO extends BaseMapper<GroupInfo> {

    //判断有没有重复的gi_uuid
    @Select("select gi_uuid from group_info where gi_uuid = #{uuid}")
    public String giUuidCheck(String uuid);
}
