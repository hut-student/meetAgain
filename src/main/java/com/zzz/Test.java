package com.zzz;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.UserDAO;
import com.google.gson.annotations.JsonAdapter;
import com.pojo.FeedBack;
import com.pojo.GroupInfo;
import com.pojo.Search;
import com.pojo.User;
import com.utils.MyMiniUtils;
import com.vo.ImMessageBean;
import com.vo.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Test {

    @Autowired
    private UserDAO userDAO;

    public static void main(String[] args) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("u_id", "u_name", "u_sex", "u_birthday", "u_head_portrait", "u_province", "u_city", "u_district", "u_address", "u_volunteer");
        queryWrapper.eq("u_id",1);
        Test test = new Test();
        System.out.println(test.userDAO.selectOne(queryWrapper));
    }

}
