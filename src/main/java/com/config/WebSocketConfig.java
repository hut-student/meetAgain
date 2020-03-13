package com.config;

import com.action.MyWebSocketController;
import com.service.GroupChatService;
import com.service.SingleChatService;
import com.service.UserGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig{

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    //以下解决WebSocket不能bean的问题
    @Autowired
    public void setSingleChatService(SingleChatService singleChatService){
        MyWebSocketController.singleChatService = singleChatService;
    }

    @Autowired
    public void setGroupChatService(GroupChatService groupChatService){
        MyWebSocketController.groupChatService = groupChatService;
    }

    @Autowired
    public void setUserGroupsService(UserGroupsService userGroupsService){
        MyWebSocketController.userGroupsService = userGroupsService;
    }

}
