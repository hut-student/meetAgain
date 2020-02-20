package com.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.UserDAO;
import com.pojo.User;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserDAO, User> {

    @Autowired
    private UserDAO userDAO;

    //头像文件夹
    @Value("${upload.headPortrait.dir}")
    private String headPortrait;

    //网站域名
    @Value("${web.site}")
    private String webSite;


    //用户身份检查
    public ResponseBean userCheck(String uId, Long time, String enCode){
        if(userDAO.selectById(uId) == null){
            return new ResponseBean(401, "该用户不存在", null);
        }else if(!MyMiniUtils.getEncryptString(uId, time).equals(enCode)){
            return new ResponseBean(402, "账号校检失败", null);
        }else{
            return new ResponseBean(000,"用户身份验证通过",null);
        }
    }

    //验证登陆
    public ResponseBean verificationLogin(String uid, Long time, String enCode){
        if( MyMiniUtils.getEncryptString(uid, time).equals(enCode)){
            //超过时间
            if(time < System.currentTimeMillis()-(1000 * 60 * 60 * 24 * 7)) {
                return new ResponseBean(301, "超过登陆时间", null);
            }
            //加密字符串正确
            LocalDateTime uLastTime = userDAO.userLastUpdateTime(uid);
            //System.out.println("用户最后一次更新时间：" + MyMiniUtils.timeMillisChangeLocalDateTime(uLastTime));
            if (time < MyMiniUtils.timeMillisChangeLocalDateTime(uLastTime)){
                //在这段时间内信息被改过，重新获取
                User user = userDAO.uIdLogin(uid);
                return new ResponseBean(200,"ok", MyMiniUtils.getEncryptString(Integer.toString(user.getuId()),System.currentTimeMillis()),user);
            }else{
                //没有被改过，则返回304
                return new ResponseBean(300,"用户账号信息未改过",MyMiniUtils.getEncryptString(uid,System.currentTimeMillis()),null);
            }
        }else{
            //加密字符串错误
            return new ResponseBean(304,"加密字符串错误",null);
        }
    }

    //用户修改头像
    public String headPortrait(int uId, MultipartFile file){
        String newFileName = MyMiniUtils.upLoadFile(file, headPortrait);
        if(newFileName != null){
            if(userDAO.updateHeadPortrait(uId, newFileName) == true){
                return "https://" + webSite + headPortrait + newFileName;
            }
        }
        return null;
    }

    //用户信息修改
    public ResponseBean updateUser(String uId, String userJson){
        User user = JSON.parseObject(userJson, User.class);
        user.setuId(Integer.parseInt(uId));
        user.setuUpdateTime(LocalDateTime.now());
        if(userDAO.updateById(user) == 1){
            User userNew = userDAO.selectById(user.getuId());
            userNew.setuHeadPortrait(webSite + headPortrait + userNew.getuHeadPortrait());
            return new ResponseBean(200,"修改成功", MyMiniUtils.getEncryptString(String.valueOf(user.getuId()),System.currentTimeMillis()),userNew);
        }
        else
            return new ResponseBean(301,"修改失败，请重试",null);
    }




}
