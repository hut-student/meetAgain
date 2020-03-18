package com.action;

import com.alibaba.fastjson.JSON;
import com.service.*;
import com.vo.ImBeans;
import com.vo.ImMessageBean;
import com.vo.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{md5}")
@Component
@CrossOrigin("*")
public class MyWebSocketController {
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocketController> webSocketSet = new CopyOnWriteArraySet<MyWebSocketController>();

    //将md5和Session绑定
    private static Map<String, Session> sessionConcurrentHashMap = new ConcurrentHashMap<>();

    //将用户id和md5进行绑定
    private static Map<Integer, String> uidAndMd5Map = new ConcurrentHashMap<>();

    //单聊服务
    @Autowired
    public static SingleChatService singleChatService;

    //群聊服务
    @Autowired
    public static GroupChatService groupChatService;

    //用户群聊关系
    @Autowired
    public static UserGroupsService userGroupsService;

    @Autowired
    public static UseFaceRecordService useFaceRecordService;

    @Autowired
    public static UserRelationshipService userRelationshipService;

    //与某个客户端连接时，用一个session来发送信息
    private Session session;

    //用户Id
    private Integer uId;


    private String md5;

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("md5") String md5) {
        this.session = session;
        this.md5 = md5;
        System.out.println("有人链接");
        //找用户单聊的全部信息
        sessionConcurrentHashMap.put(md5, session);//在建立连接的时候就将用户id和Session绑定
        webSocketSet.add(this); //将每个客户的webSocket放入
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        uidAndMd5Map.remove(uId);
        sessionConcurrentHashMap.remove(md5);
        webSocketSet.remove(this);
    }

    /**
     * 发生错误时调用
     *
     * @param session
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误" + error.getMessage());
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
        System.out.println(message);
        MessageBean messageBean = JSON.parseObject(message, MessageBean.class);
        System.out.println(messageBean);
        if (messageBean.getType() == 0) {
            String heartMsg = messageBean.getMsg();
            if (heartMsg.length() == 6) {
                //没有用户id
            } else {
                //当存在用户id时
                Integer uId = Integer.valueOf(heartMsg.substring(6));
                this.uId = uId;
                System.out.println(uId);
                //将用户id和md5绑定
                uidAndMd5Map.put(uId, md5);
                //查看数据库中是否有未接受的信息
                ArrayList<ImBeans> imBeansArrayList = singleChatService.findUserSingleChat(uId);
                if(imBeansArrayList != null){
                    MessageBean messageBeanSendChat = new MessageBean();
                    messageBeanSendChat.setType(2);
                    messageBeanSendChat.setImBeansArray(imBeansArrayList);
                    synchronized (session){
                        session.getAsyncRemote().sendText(JSON.toJSONString(messageBeanSendChat));
                    }
                }
                //查看是否有人扫了用户的脸
                MessageBean useFaceRecodeMessageBean = useFaceRecordService.findUseUid(uId);
                if(useFaceRecodeMessageBean != null){
                    sessionConcurrentHashMap.get(md5).getAsyncRemote().sendText(JSON.toJSONString(useFaceRecodeMessageBean));
                }

                //被别人关注，提示下
                MessageBean findAttentionMyselfMessageBean = userRelationshipService.findAttentionMyself(uId);
                if(findAttentionMyselfMessageBean != null){
                    session.getAsyncRemote().sendText(JSON.toJSONString(findAttentionMyselfMessageBean));
                }


            }
        }
        if (messageBean.getType() == 1) {
            ImMessageBean imMessageBean = messageBean.getImMessage();
            //获得接受者的id
            Integer toUserId = imMessageBean.getToUid();
            //查看接受者是否在线
            String toUserMd5 = uidAndMd5Map.get(toUserId);
            if (toUserMd5 == null || toUserMd5.length() == 0) {
                //当用户不在线时，将消息存入数据库
                if (singleChatService.insertOneChat(imMessageBean.getuId(), imMessageBean.getToUid(), imMessageBean.getContent(), imMessageBean.getTime(), imMessageBean.getImId())){
                    //成功存入数据库
                    return;
                }else {
                    //存入数据库失败,再试一次
                    singleChatService.insertOneChat(imMessageBean.getuId(), imMessageBean.getToUid(), imMessageBean.getContent(), imMessageBean.getTime(), imMessageBean.getImId());
                    return;
                }
            }
            Session toUserSession = sessionConcurrentHashMap.get(toUserMd5);
            messageBean.getImMessage().setTargetUid(toUserId);
            synchronized (toUserSession) {
                toUserSession.getAsyncRemote().sendText(JSON.toJSONString(messageBean));
            }
        }
        /*//将message转换为SocketMsg对象
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        SocketMsg socketMsg = JSON.parseObject(message, SocketMsg.class);
        socketMsg.setFromUserId(uId);
        Session fromSession = sessionConcurrentHashMap.get(uId);//发送者的Session
        if(socketMsg.getToUserId() == null && socketMsg.getGiId() == null){
            fromSession.getAsyncRemote().sendText(new ResponseBean(404, "参数错误", null).toString());
            return;
        }
        if (socketMsg.getType() == 1) {
            //单聊，寻找发送者和接受者
            Session toSession = sessionConcurrentHashMap.get(socketMsg.getToUserId());//接受这的Session
            //将发送内容存入数据库
            ResponseBean responseBean = singleChatService.insertOneChat(socketMsg.getFromUserId(), socketMsg.getToUserId(), socketMsg.getMsg());
            //发送给接受者
            if (toSession != null) {
                //发送者也与服务器建立连接,于是发送消息
//                fromSession.getAsyncRemote().sendObject(responseBean);
//                toSession.getAsyncRemote().sendObject(responseBean);
                fromSession.getAsyncRemote().sendText(responseBean.toString());
                toSession.getAsyncRemote().sendText(responseBean.toString());
            } else {
                //发送者没有与服务器建立连接
                fromSession.getAsyncRemote().sendText("对方未在线");
//                fromSession.getAsyncRemote().sendObject(responseBean);
                fromSession.getAsyncRemote().sendText(responseBean.toString());
            }
        } else if (socketMsg.getType() == 2) {
            //群聊
            //调用下面那个方法进行群发
            //1、将这个信息存到这个群里
            ResponseBean responseBean = groupChatService.addOneGroupChat(socketMsg.getFromUserId(), socketMsg.getToUserId(), socketMsg.getGcId(), socketMsg.getGiId(), socketMsg.getMsg());
            if (responseBean.getCode() == 403) {
//                fromSession.getAsyncRemote().sendObject(responseBean);
                fromSession.getAsyncRemote().sendText(responseBean.toString());
            }
            //2、当这个群里面的人在线时，就给他们发
            List<Integer> groupPeopleList = userGroupsService.groupPeople(socketMsg.getGiId());
            for (MyWebSocketController myWebSocketController : webSocketSet) {
                if (groupPeopleList.indexOf(myWebSocketController.uId) != -1) {
//                    myWebSocketController.session.getAsyncRemote().sendObject(responseBean);
                    myWebSocketController.session.getAsyncRemote().sendText(responseBean.toString());
                }
            }
        } else {
//            fromSession.getAsyncRemote().sendObject(new ResponseBean(404, "参数错误", null));
            fromSession.getAsyncRemote().sendText(new ResponseBean(404, "参数错误", null).toString());
        }
*/

    }


    /**
     * 群发的自定义消息
     * 参考作用，已淘汰
     *
     * @param content
     */
/*    public void broadCast(String content, Integer groupChatId) {
        for (MyWebSocketController myWebSocketController : webSocketSet) {
            //调用getAsyncRemote方法实现异步发送消息
            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
            myWebSocketController.session.getAsyncRemote().sendText(content);
        }

    }*/

}
