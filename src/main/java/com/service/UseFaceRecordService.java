package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.UseFaceRecordDAO;
import com.dao.UserDAO;
import com.pojo.UseFaceRecord;
import com.pojo.User;
import com.vo.MessageBean;
import com.vo.SystemMessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UseFaceRecordService {

    @Autowired
    private UseFaceRecordDAO useFaceRecordDAO;

    @Autowired
    private UserDAO userDAO;

    @Value("${web.site}")
    private String webSite;

    @Value("${upload.headPortrait.dir}")
    private String headPhoto;

    //查看有谁扫了该用户的脸
    public MessageBean findUseUid(Integer uId){
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("sao_user_id", uId);
            queryWrapper.ne("use_user_id", uId);
            queryWrapper.eq("sum", 0);
            queryWrapper.orderByDesc("time");
            List<UseFaceRecord> useFaceRecordList = useFaceRecordDAO.selectList(queryWrapper);
            if(useFaceRecordList == null || useFaceRecordList.size() == 0){
                return null;
            }
            ArrayList<SystemMessageBean> systemMessageBeanArrayList = new ArrayList<>();
            for(UseFaceRecord useFaceRecord : useFaceRecordList){
                SystemMessageBean systemMessageBean = new SystemMessageBean();
                User user = userDAO.selectById(useFaceRecord.getUseUserId());

                systemMessageBean.setType(0); //被别人扫了脸
                systemMessageBean.setTitle("系统通知");
                systemMessageBean.setTime(System.currentTimeMillis());
                systemMessageBean.setTargetUid(useFaceRecord.getUseUserId());
                systemMessageBean.setUid(useFaceRecord.getSaoUserId());
                systemMessageBean.setRead(false);
                systemMessageBean.setHeader("https://" + webSite + headPhoto + user.getuHeadPortrait());
                systemMessageBean.setNickName(user.getuName());

                systemMessageBeanArrayList.add(systemMessageBean);
            }
            //将useFaceRecode中的sum改为1；
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("sao_user_id", uId);
            UseFaceRecord useFaceRecord = new UseFaceRecord();
            useFaceRecord.setSum(1);
            useFaceRecordDAO.update(useFaceRecord, queryWrapper);

            //生成返回数据
            MessageBean messageBean = new MessageBean();
            messageBean.setSystemMessageArray(systemMessageBeanArrayList);
            messageBean.setType(4);
            return messageBean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //推送及时的关注人Search
}
