package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.User;
import com.pojo.UserRelationship;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRelationshipDAO extends BaseMapper<UserRelationship> {

    @Select("select himself_uid from user_relationship where myself_uid = #{uId}")
    public List<Integer> selectUserFriends(Integer uId);


    /**
     * 双关的用户,获得公开信息
     * @param myUid
     * @return
     */
    @Select("SELECT u_id,u_name,u_sex,u_birthday,u_head_portrait,u_province,u_city,u_district,u_address,u_volunteer FROM user WHERE u_id in (select myself_uid from user_relationship where himself_uid = #{uId} and myself_uid in (select himself_uid from user_relationship where myself_uid = #{uId}))")
    public List<User> findMutualConcern(int myUid);
}
