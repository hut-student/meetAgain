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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    //验证登陆
    @RequestMapping("verificationLogin")
    public ResponseBean verificationLogin(String uid, Long time, String enCode) {
        return userService.verificationLogin(uid, time, enCode);
    }

    //用户修改头像
    @RequestMapping("updateHeadPhoto")
    public ResponseBean uploadHeadPortrait(@RequestParam("uId") String uid, @RequestParam("enCode") String enCode, @RequestParam("time") Long time, @RequestParam("headPhoto") MultipartFile file) {
        ResponseBean responseBean = userService.userCheck(uid, time, enCode);
        if(responseBean.getCode() != 000){
            return responseBean;
        }
        if (file.isEmpty()) {
            return new ResponseBean(405, "未上传图片", null);
        }
        if (file.getSize() > 350*1024) {
            return new ResponseBean(404, "上传文件过大", null);
        }
        String url = userService.headPortrait(Integer.parseInt(uid), file);
        if (url != null) {
            return new ResponseBean(200, "头像修改成功", MyMiniUtils.getEncryptString(uid, System.currentTimeMillis()), url);
        }
        return new ResponseBean(403, "上传异常", null);
    }

    //用户信息修改
    @RequestMapping("user/update")
    public ResponseBean updateUser(String uId, Long time,String enCode , String userJson) {
        ResponseBean responseBean = userService.userCheck(uId, time, enCode);
        if(responseBean.getCode() != 000){
            return responseBean;
        }
        return userService.updateUser(uId, userJson);
    }

}
