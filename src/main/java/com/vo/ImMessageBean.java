package com.vo;

import java.io.Serializable;

public class ImMessageBean implements Serializable {

    private String imId;
    private Long time;
    private Integer uId;    //发送者id
    private Integer toUid;  //接受者id
    private String content; //内容
    private boolean isRead; //是否已读
    private String header;  //头像
    private String nickName;//发送者的昵称
    private Integer targetUid; //这条记录发给哪个用户

    @Override
    public String toString() {
        return "ImMessageBean{" +
                "imId='" + imId + '\'' +
                ", time=" + time +
                ", uId=" + uId +
                ", toUid=" + toUid +
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                ", header='" + header + '\'' +
                ", nickName='" + nickName + '\'' +
                ", targetUid='" + targetUid + '\'' +
                '}';
    }

    public Integer getTargetUid() {
        return targetUid;
    }

    public void setTargetUid(Integer targetUid) {
        this.targetUid = targetUid;
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
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
