package com.action;

import com.pojo.User;
import com.service.UserService;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    //验证登陆
    @RequestMapping("verificationLogin")
    public ResponseBean verificationLogin(String uid, Long time, String enCode){
        return userService.verificationLogin(uid, time, enCode);
    }

    //用户修改头像
    @RequestMapping("updateHeadPhoto")
    public ResponseBean uploadHeadPortrait(@RequestParam("uId") String uid, @RequestParam("headPhoto") MultipartFile file){
        if(file.getOriginalFilename().equals("")){
            return new ResponseBean(301,"请选择一张图片",null);
        }
        String url = userService.headPortrait(Integer.parseInt(uid), file);
        if(url != null){
            return new ResponseBean(200,"头像修改成功", MyMiniUtils.getEncryptString(uid,System.currentTimeMillis()), url);
        }
        return new ResponseBean(300,"修改失败,请联系管理员",null);
    }

    //用户信息修改
//    public ResponseBean updateUser(int uId, String ){
//
//    }


}
