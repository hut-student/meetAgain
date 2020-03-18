package com.vo;

import java.io.Serializable;

public class SystemMessageBean implements Serializable {

    private Integer type;       //0：扫脸  1：好友关注
    private Long time;
    private Integer targetUid;  //目标用户uid（关注别人的用户、扫脸的用户）
    private Integer uid;        //这个消息被推送的用户id（被关注、被扫脸的人）
    private String title;       //消息标题
    private String content;     //消息内容
    private boolean isRead;     //是否已读
    private String header;      //目标用户头像url
    private String nickName;     //目标用户昵称

    @Override
    public String toString() {
        return "SystemMessageBean{" +
                "type=" + type +
                ", time=" + time +
                ", targetUid=" + targetUid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                ", header='" + header + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getTargetUid() {
        return targetUid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setTargetUid(Integer targetUid) {
        this.targetUid = targetUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
