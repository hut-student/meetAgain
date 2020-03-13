package com.zzz;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.annotations.JsonAdapter;
import com.pojo.FeedBack;
import com.pojo.GroupInfo;
import com.pojo.Search;
import com.pojo.User;
import com.utils.MyMiniUtils;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        String time = "2018-12-11T00:00";
        LocalDateTime localDateTime = LocalDateTime.parse(time);
        System.out.println(localDateTime);
    }

}
