package com.action;

import com.alibaba.fastjson.JSON;
import com.pojo.User;
import com.service.TeleService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/tele")
public class TeleCotroller {

    @Autowired
    private TeleService teleService;


    @RequestMapping("yzm")
    public String yzmSend(String tele){
        return teleService.yzmSend(tele);
    }

    @RequestMapping("teleRegister")
    public String register(String tele, String code){
        if(teleService.teleRegister(tele, code)){
            return JSON.toJSONString(teleService.showOneUser(tele));
        }else{
            return "{\"return_code\":\"5001\"}"; //验证码输入错误
        }
    }
    @RequestMapping("teleLogin")
    public String login(String tele, String code){
        User user = teleService.teleLogin(tele, code);
        if (user != null)
            return JSON.toJSONString(user);
        return "{\"return_code\":\"5001\"}";
    }


}
