package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.SingleChatDAO;
import com.dao.UserDAO;
import com.pojo.Comment;
import com.pojo.SingleChat;
import com.pojo.User;
import com.utils.MyMiniUtils;
import com.vo.ImBeans;
import com.vo.ImMessageBean;
import com.vo.MessageBean;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SingleChatService {

    @Autowired
    private SingleChatDAO singleChatDAO;

    @Autowired
    private UserDAO userDAO;

    @Value("${web.site}")
    private String webSite;

    @Value("${upload.headPortrait.dir}")
    private String headPhoto;

    //记录单聊信息
    public boolean insertOneChat(Integer fromUid, Integer toUid, String content, Long time, String scImId) {
        SingleChat singleChat = new SingleChat(fromUid, toUid, content, MyMiniUtils.localDateTimeChangeTimeMillis(time));
        singleChat.setScImId(scImId);
        try {
            if (singleChatDAO.insert(singleChat) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //寻找当前用户与其他用户未接受的信息
    public ArrayList<ImBeans> findUserSingleChat(Integer uId) {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("to_uid", uId);
            queryWrapper.orderByDesc("from_uid");
            queryWrapper.orderByAsc("sc_time");
            List<SingleChat> singleChats = singleChatDAO.selectList(queryWrapper);
            if (singleChats == null || singleChats.size() == 0) {
                //没有未发送的消息
                return null;
            }
            //用户的所有聊天记录
            ArrayList<ImBeans> imBeansList = new ArrayList<>();
            //单个用户的聊天记录
            ImBeans imBeans = new ImBeans();
            //将第一个的记录imId放入
            imBeans.setImId(singleChats.get(0).getScImId());

            int i;
            //一个放imMessageBean的容器
            ArrayList<ImMessageBean> imMessageBeans = new ArrayList<>();

            for (i = 0; i < singleChats.size(); i++) {
                SingleChat singleChat = singleChats.get(i);
                User user = userDAO.selectById(singleChat.getFromUid());
                //一个imMessageBean对象创建,新的聊天语句
                ImMessageBean imMessageBean = new ImMessageBean();
                //imMessageBean数据的填充
                imMessageBean.setImId(singleChat.getScImId()); //多个用户的消息组
                imMessageBean.setTargetUid(singleChat.getToUid());
                imMessageBean.setContent(singleChat.getScContent());
                imMessageBean.setRead(false);
                imMessageBean.setTime(MyMiniUtils.timeMillisChangeLocalDateTime(singleChat.getScTime()));
                imMessageBean.setToUid(singleChat.getToUid());
                imMessageBean.setuId(singleChat.getFromUid());
                imMessageBean.setHeader("https://" + webSite + headPhoto + user.getuHeadPortrait());
                imMessageBean.setNickName(user.getuName());
                //将单条聊天记录放入这个用户的聊天记录中
                imMessageBeans.add(imMessageBean);
                //检测有没有下一条，或者下一条是不是同一个用户的聊天记录
                if (i == (singleChats.size() - 1)) { //表示当前是最后一条记录
                    //将这个用户的聊天记录放入单个用户的聊天记录中
                    imBeans.setArray(imMessageBeans);
                    imBeansList.add(imBeans);
                    break;
                }
                if (imBeans.getImId() != singleChats.get(i + 1).getScImId()) {
                    //表示下一条记录的用户变了
                    //将当前用户的聊天记录放入
                    imBeans.setArray(imMessageBeans);
                    imBeansList.add(imBeans);
                    //创建一个新的用户聊天记录容器
                    imBeans = new ImBeans();
                    imBeans.setImId(singleChats.get(i + 1).getScImId());
                    //将imMessageBeans（单个用户全部聊天记录的结合）刷新
                    imMessageBeans = new ArrayList<>();
                }
            }
            //删除已读取的数据
            singleChatDAO.delete(queryWrapper);
            return imBeansList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
