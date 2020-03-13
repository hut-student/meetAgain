package com.vo;

import java.io.Serializable;

public class SocketMsg implements Serializable {

    private Integer imId;
    private Long time;
    private Integer uId;    //发送者id
    private Integer toUid;  //接受者id
    private String content; //内容
    private boolean isRead; //是否已读
    private String header;  //头像
    private String nickName;//发送者的昵称

    @Override
    public String toString() {
        return "SocketMsg{" +
                "imId=" + imId +
                ", time=" + time +
                ", uId=" + uId +
                ", toUid=" + toUid +
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                ", header='" + header + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public Integer getImId() {
        return imId;
    }

    public void setImId(Integer imId) {
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
