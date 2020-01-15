package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserDAO extends BaseMapper<User> {

    @Select("SELECT * from user")
    public List<Map> findAllUser();

}
