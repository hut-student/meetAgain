package com.action;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@RestController
@CrossOrigin("*")
public class MyWebSocket {
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端连接时，用一个session来发送信息
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("有新连接加入！！当前人数为：" + webSocketSet.size() + "人次");
        this.session.getAsyncRemote().sendText("已成功连接上服务器--->>当前在线人数是：" + webSocketSet.size() + "人次");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        System.out.println("有一处连接关闭！！当前人数为：" + webSocketSet.size() + "人次");

    }

    /**
     * 发生错误时调用
     *
     * @param session
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //调用下面那个方法进行群发
        broadCast(message);
    }


    /**
     * 群发的自定义消息
     *
     * @param message
     */
    public void broadCast(String message) {
        for (MyWebSocket myWebSocket : webSocketSet) {
            //调用getAsyncRemote方法实现异步发送消息
            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
            myWebSocket.session.getAsyncRemote().sendText(message);
        }

    }

}
