package com.zzz;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pojo.FeedBack;
import com.pojo.Search;
import com.pojo.User;
import com.utils.MyMiniUtils;

import java.time.LocalDateTime;

public class Test {

    public static void main(String[] args) {
//        FeedBack feedBack = new FeedBack();
//        feedBack.setuId(1);
//        feedBack.setfContent("快点搞");
//        System.out.println(JSON.toJSONString(feedBack));
//        System.out.println(System.currentTimeMillis() + "---" + MyMiniUtils.getEncryptString(feedBack.getuId().toString(),System.currentTimeMillis()));
        System.out.println(MyMiniUtils.getEncryptString("1",1582185290643L).equals("129E84EA13593B02E301442D69D1E0E7"));
    }

}
