package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.SingleChatDAO;
import com.pojo.SingleChat;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SingleChatService {

    @Autowired
    private SingleChatDAO singleChatDAO;

    //记录单聊信息
    public ResponseBean insertOneChat(Integer fromUid, Integer toUid, String content){
        LocalDateTime putTime = LocalDateTime.now();
        SingleChat singleChat = new SingleChat(fromUid,toUid, content, putTime);
        System.out.println(singleChat);
       if(singleChatDAO.insert(singleChat) == 1){
           QueryWrapper queryWrapper = new QueryWrapper();
           queryWrapper.eq(true,"from_uid",fromUid)
                   .eq(true,"to_uid",toUid)
                   .eq(true,"sc_time",putTime);
           return new ResponseBean(200,"ok",singleChatDAO.selectOne(queryWrapper));
       }else {
           return new ResponseBean(403,"后台参数异常", null);
       }
    }

    //寻找当前用户与其他用户的多少条信息
    //public ResponseBean selectUserSingleChat(Integer fromUid,)
}
