package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserDAO extends BaseMapper<User> {

    @Select("SELECT u_id,u_name,u_sex,u_birthday,u_head_portrait,u_tele,u_province,u_city,u_district,u_address,u_qq,u_wechat,u_microblog,u_volunteer,u_update_time FROM user WHERE u_tele=#{t} and u_pw=#{pw}")
    public User TelePwLogin(@Param("t") String tele, @Param("pw") String pw);

    /**
     * 通过电话号码查看是否有该用户
     * @param tele
     * @return
     */
    @Select("select u_id from user where u_tele=#{t}")
    public String findOneUser(@Param("t") String tele);

    //给验证码临时表添加数据
    @Insert("insert into tele_tem(tele,yzm,date) values(#{tele}, #{yzm}, #{time}) on duplicate key update yzm = #{yzm}, date = #{time}")
    public boolean addTemYZM(String tele, String yzm, LocalDateTime time);

    //验证验证码是否正确
   @Select("select tele from tele_tem where tele=#{tele} and yzm=#{yzm}")
   public String judgeYZM(String tele, String yzm);

    //注册新用户
    @Insert("insert into user(u_name,u_pw,u_tele,u_update_time) values(#{name}, #{pw}, #{tele}, #{time})")
    public boolean registerUser(@Param("tele") String tele, @Param("pw") String pw, @Param("name") String name, @Param("time") LocalDateTime time);

    //电话号码登录处理
    @Select("SELECT u_id,u_name,u_sex,u_birthday,u_head_portrait,u_tele,u_province,u_city,u_district,u_address,u_qq,u_wechat,u_microblog,u_volunteer,u_update_time FROM user WHERE u_tele=#{tele}")
    public User TeleLogin(String tele);

    //用户id登录
    @Select("SELECT u_id,u_name,u_sex,u_birthday,u_head_portrait,u_tele,u_province,u_city,u_district,u_address,u_qq,u_wechat,u_microblog,u_volunteer,u_update_time FROM user WHERE u_id=#{uId}")
    public User uIdLogin(String uId);

    //获得用户信息更新时间
    @Select("select u_update_time FROM user WHERE u_id = #{uId}")
    public LocalDateTime userLastUpdateTime(String uId);

    //用户修改头像
    @Update("UPDATE user set u_head_portrait = #{newDir} WHERE u_id = #{id}")
    public boolean updateHeadPortrait(@Param("id") int uid, @Param("newDir") String newName);
}
