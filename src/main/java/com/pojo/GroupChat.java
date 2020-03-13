package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChat implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer gcId;
    private Integer fromUid;
    private Integer toUid;
    private Integer toGcId;
    private Integer giId;
    private String gcContent;
    private LocalDateTime gcPutTime;

    @TableField(exist = false)
    private Long gcTime;
    @TableField(exist = false)
    private User fromUser;
    @TableField(exist = false)
    private User toUser;

    public GroupChat() {
    }

    //在群里的普通聊天


    public GroupChat(Integer fromUid, Integer giId, String gcContent) {
        this.fromUid = fromUid;
        this.giId = giId;
        this.gcContent = gcContent;
    }

    //在群里给被人回复
    public GroupChat(Integer fromUid, Integer toUid,Integer toGcId, Integer giId, String gcContent) {
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.toGcId = toGcId;
        this.giId = giId;
        this.gcContent = gcContent;
    }

    @Override
    public String toString() {
        return "GroupChat{" +
                "gcId=" + gcId +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", toGcId=" + toGcId +
                ", giId=" + giId +
                ", gcContent='" + gcContent + '\'' +
                ", gcPutTime=" + gcPutTime +
                ", gcTime=" + gcTime +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                '}';
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Integer getToGcId() {
        return toGcId;
    }

    public void setToGcId(Integer toGcId) {
        this.toGcId = toGcId;
    }

    public Integer getGcId() {
        return gcId;
    }

    public void setGcId(Integer gcId) {
        this.gcId = gcId;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public Integer getGiId() {
        return giId;
    }

    public void setGiId(Integer giId) {
        this.giId = giId;
    }

    public String getGcContent() {
        return gcContent;
    }

    public void setGcContent(String gcContent) {
        this.gcContent = gcContent;
    }

    public LocalDateTime getGcPutTime() {
        return gcPutTime;
    }

    public void setGcPutTime(LocalDateTime gcPutTime) {
        this.gcTime = MyMiniUtils.timeMillisChangeLocalDateTime(gcPutTime);
        this.gcPutTime = gcPutTime;
    }

    public Long getGcTime() {
        return gcTime;
    }

    public void setGcTime(Long gcTime) {
        this.gcTime = gcTime;
    }
}
