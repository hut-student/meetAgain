package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.GroupChatDAO;
import com.dao.UserDAO;
import com.pojo.GroupChat;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GroupChatService {

    @Autowired
    private GroupChatDAO groupChatDAO;

    @Autowired
    private UserDAO userDAO;

    //添加一条群聊消息
    public ResponseBean addOneGroupChat(int fromUid,Integer toUid, Integer toGcId, Integer giId, String content){
        GroupChat groupChat;
        if(toUid != null || toGcId != null){
             groupChat = new GroupChat(fromUid,toUid, toGcId, giId, content);
        }else {
            groupChat = new GroupChat(fromUid,giId,content);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        groupChat.setGcPutTime(localDateTime);
        if(groupChatDAO.insert(groupChat) == 1){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("from_uid", groupChat.getFromUid());
            queryWrapper.eq("gc_put_time", localDateTime);
            groupChat = groupChatDAO.selectOne(queryWrapper);
            //只查询用户的id、昵称、头像
            QueryWrapper queryWrapperFromUser = new QueryWrapper();
            queryWrapperFromUser.select("u_id","u_uuid","u_name","u_head_portrait").eq("u_id",groupChat.getFromUid());
            groupChat.setFromUser(userDAO.selectOne(queryWrapperFromUser));
            if(groupChat.getToUid() != null){
                QueryWrapper queryWrapperToUser = new QueryWrapper();
                queryWrapperToUser.select("u_id","u_uuid","u_name","u_head_portrait").eq("u_id",groupChat.getToUid());
                groupChat.setToUser(userDAO.selectOne(queryWrapperToUser));
            }
            return new ResponseBean(200,"ok", groupChat);
        }else {
            return new ResponseBean(403,"后台异常", null);
        }
    }

}
