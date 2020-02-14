package com.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.UserDAO;
import com.pojo.User;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
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

    //验证登陆
    public ResponseBean verificationLogin(String uid, Long time, String enCode){
        if( MyMiniUtils.getEncryptString(uid, time).equals(enCode)){
            //超过时间
            if(time < System.currentTimeMillis()-(1000 * 60 * 60)) {
                return new ResponseBean(301, "超过登陆时间,目前设置为一个小时", null);
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
                return new ResponseBean(304,"用户账号信息未改过",null);
            }
        }else{
            //加密字符串错误
            return new ResponseBean(300,"加密字符串错误",null);
        }
    }

    //用户修改头像
    public String headPortrait(int uId, MultipartFile file){
        String newFileName = MyMiniUtils.upLoadFile(file, headPortrait);
        if(newFileName != null){
            if(userDAO.updateHeadPortrait(uId, newFileName) == true){
                return webSite + headPortrait + newFileName;
            }
        }
        return null;
    }

    //用户信息修改
    public int updateUser(User user){
        return userDAO.updateById(user);
    }


}
