package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.FeedBackDAO;
import com.pojo.FeedBack;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class FeedBackService extends ServiceImpl<FeedBackDAO, FeedBack> {

    @Autowired
    private FeedBackDAO feedBackDAO;

    @Autowired
    private UserService userService;

    //反馈图片文件夹
    @Value("${upload.feedBack.dir}")
    private String feedBackDir;

    //网站域名
    @Value("${web.site}")
    private String webSite;


    //提交反馈
    public ResponseBean insertFeedBack(Integer uId, Long time, String enCode, String feedBackJson) {
        FeedBack feedBack = JSON.parseObject(feedBackJson, FeedBack.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (uId == null) {
            ResponseBean responseBean = userService.userCheck(uId.toString(), time, enCode);
            if (responseBean.getCode() != 000) {
                return responseBean;
            }
            feedBack.setuId(uId);
            queryWrapper.eq("u_id", feedBack.getuId());
        }
        feedBack.setfTime(LocalDateTime.now());
        queryWrapper.orderByDesc("f_time");
        if (feedBackDAO.insert(feedBack) == 1) {
            return new ResponseBean(200, "提交成功", null);
        } else {
            return new ResponseBean(403, "数据异常，提交失败", null);
        }
    }

    //用户Id查找反馈
    public ResponseBean select(Integer uId, Long time, String enCode) {
        ResponseBean responseBean = userService.userCheck(uId.toString(), time, enCode);
        if (responseBean.getCode() != 000) {
            return responseBean;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("u_id", uId);
        queryWrapper.orderByDesc("f_time");
        return new ResponseBean(200, "查找成功", feedBackDAO.selectList(queryWrapper));
    }

    //上传图片
    public ResponseBean uploadPhoto(MultipartFile file){
        String newName = MyMiniUtils.upLoadFile(file, feedBackDir);
        if(newName != null){
            return new ResponseBean(200,"上传成功","https://" + webSite + feedBackDir + newName);
        }
        return new ResponseBean(403,"上传图片异常", null);
    }
}
