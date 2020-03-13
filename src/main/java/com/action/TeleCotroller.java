package com.action;

import com.alibaba.fastjson.JSON;
import com.pojo.User;
import com.service.TeleService;
import com.service.UserService;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/tele")
public class TeleCotroller {

    @Autowired
    private TeleService teleService;

    //头像文件夹
    @Value("${upload.headPortrait.dir}")
    private String headPortrait;

    //网站域名
    @Value("${web.site}")
    private String webSite;

    @RequestMapping("yzm")
    public ResponseBean yzmSend(String tele) {
        return new ResponseBean(Long.valueOf(teleService.yzmSend(tele).substring(16,21)), "ok", null);
    }


    /**
     * 电话号码注册以及登陆
     *
     * @param tele
     * @param code
     * @return
     */
    @RequestMapping("teleLoginOrRegister")
    public ResponseBean login(String tele, String code) {
        if (teleService.selectUser(tele)) {
            User user = teleService.teleLogin(tele, code);
            if (user != null){
                user.setuHeadPortrait("https://" + webSite + headPortrait + user.getuHeadPortrait());
                System.out.println(user.getuHeadPortrait());
                return new ResponseBean(200, "ok", MyMiniUtils.getEncryptString(String.valueOf(user.getuId()), System.currentTimeMillis()), user);
            }
            return new ResponseBean(400, "ok", null);
        }
        if (teleService.teleRegister(tele, code)) {
            User user = teleService.showOneUser(tele);
            return new ResponseBean(200, "ok", MyMiniUtils.getEncryptString(String.valueOf(user.getuId()), System.currentTimeMillis()), user);
        } else {
            return new ResponseBean(400, "ok", null);//验证码输入错误
        }
    }




}
