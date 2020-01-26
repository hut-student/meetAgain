package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface UserDAO extends BaseMapper<User> {

    @Select("SELECT * from user")
    public List<Map> findAllUser();

    @Select("SELECT u_id,u_name,u_sex,u_birthday,u_head_portrait,u_tele,u_province,u_city,u_district,u_address,u_qq,u_wechat,u_microblog,u_volunteer,u_update_time FROM user WHERE u_tele=#{t} and u_pw=#{pw}")
    public User TelePwLogin(@Param("t") String tele, @Param("pw") String pw);

    /**
     * 通过电话号码查看是否有该用户
     * @param tele
     * @return
     */
    @Select("select u_id from user where u_tele=#{t}")
    public boolean findOneUser(@Param("t") String tele);

    /**
     * 存贮当前验证码
     * @param tele
     * @param yzm
     */
    @Update("update user set u_yzm = #{yzm} where u_tele = #{tele}")
    public int saveYZM(@Param("tele") String tele, @Param("yzm") String yzm);


    //给验证码临时表添加数据
    @Insert("insert into tele_tem(tele,yzm) values(#{tele}, #{yzm}) on duplicate key update yzm = #{yzm}")
    public boolean addTemYZM(String tele, String yzm);

    //验证验证码是否正确
    @Select("select tele from tele_tem where tele=#{tele} and yzm=#{yzm}")
    public boolean judgeYZM(String tele, String yzm);

    //注册新用户
    @Insert("insert into user(u_name,u_pw,u_tele) values(#{name}, #{pw}, #{tele})")
    public boolean registerUser(@Param("tele") String tele, @Param("pw") String pw, @Param("name") String name);

    //电话号码登录处理
    @Select("SELECT u_id,u_name,u_sex,u_birthday,u_head_portrait,u_tele,u_province,u_city,u_district,u_address,u_qq,u_wechat,u_microblog,u_volunteer,u_update_time FROM user WHERE u_tele=#{tele}")
    public User TeleLogin(String tele);
}
