package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.FaceGroupInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaceGroupInfoDAO extends BaseMapper<FaceGroupInfo> {

    //查找最大的id
    @Select("SELECT MAX(group_id) from face_group_info;")
    public Integer selectMaxId();

    //查找全部的group_id
    @Select("select group_id from face_group_info")
    public List<String> selectGroupId();
}
