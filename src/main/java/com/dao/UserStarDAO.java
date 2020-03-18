package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.UserStar;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStarDAO extends BaseMapper<UserStar> {

    @Select("select s_id from user_star where u_id = #{uId}")
    public List<Integer> selectSearchId(Integer uId);
}
