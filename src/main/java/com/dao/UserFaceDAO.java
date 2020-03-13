package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.UserFace;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFaceDAO extends BaseMapper<UserFace> {

    @Select("select face_id, uid, url , `user`.u_head_portrait as header, `user`.u_name as nickName, `user`.u_signature as introduce from user_face, `user` where uid = u_id and uid = #{uid} and face_id = #{faceId}")
    public UserFace selectUserFace(Integer uid, String faceId);

}
