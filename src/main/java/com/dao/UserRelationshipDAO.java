package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.UserRelationship;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRelationshipDAO extends BaseMapper<UserRelationship> {

    @Select("select himself_uid from user_relationship where myself_uid = #{uId}")
    public List<Integer> selectUserFriends(Integer uId);
}
