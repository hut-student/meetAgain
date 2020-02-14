package com.service;

import com.dao.UserDAO;
import com.pojo.User;
import com.utils.HttpUtils;
import com.utils.MyMiniUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.utils.MyMiniUtils.randomNumber;

@Service
public class TeleService {

    @Autowired
    private UserDAO userDAO;

    //检查是否有这个用户
    public boolean selectUser(String tele){
        return (userDAO.findOneUser(tele) == null) ? false : true;
    }

    //电话号码登录
    public User showOneUser(String tele) {
        return userDAO.TeleLogin(tele);
    }

    //验证码登录
    public User teleLogin(String tele, String code) {
        return userDAO.judgeYZM(tele, code) != null ? userDAO.TeleLogin(tele) : null;
    }

    //验证码注册
    public boolean teleRegister(String tele, String code) {
        return userDAO.judgeYZM(tele, code) != null ? userDAO.registerUser(tele, UUID.randomUUID().toString(), "用户" + MyMiniUtils.randomNumber("0123456789", 4), LocalDateTime.now()) : false;
    }

    //发送验证码
    public String yzmSend(String tele) {
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        String appcode = "bd6c0dc8a0af423e997f402716706003";
        String code = MyMiniUtils.randomNumber("0123456789", 6);
        userDAO.addTemYZM(tele, code, LocalDateTime.now());
        System.out.println("验证码是" + code);
        //userDAO.saveYZM(tele, code);
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", tele);
        querys.put("param", "code:" + code);
        querys.put("tpl_id", "TP1711063");
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "500";
    }


}
