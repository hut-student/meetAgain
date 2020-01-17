package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserDAO extends BaseMapper<User> {

    @Select("SELECT * from user")
    public List<Map> findAllUser();

    @Select("SELECT u_id,u_name,u_pw,u_sex,u_birthday,u_head_portrait,u_tele,u_province,u_city,u_district,u_address,u_qq,u_wechat,u_microblog,u_volunteer,u_update_time FROM user WHERE u_tele=#{t} and u_pw=#{pw}")
    public User TeleLogin(@Param("t") String tele, @Param("pw") String pw);

}
