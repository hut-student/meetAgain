package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ApplicationFriend implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer afId;
    private Integer fromUid;
    private Integer toUid;
    private String validationMsg;
    private LocalDateTime putTime;

    @TableField(exist = false)
    private Long time;

    public ApplicationFriend() {
    }

    public ApplicationFriend(Integer fromUid, Integer toUid, LocalDateTime putTime) {
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.putTime = putTime;
    }

    public ApplicationFriend(Integer fromUid, Integer toUid, String validationMsg, LocalDateTime putTime) {
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.validationMsg = validationMsg;
        this.putTime = putTime;
    }

    @Override
    public String toString() {
        return "ApplicationFriend{" +
                "afId=" + afId +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", validationMsg='" + validationMsg + '\'' +
                ", putTime=" + putTime +
                ", time=" + time +
                '}';
    }

    public Integer getAfId() {
        return afId;
    }

    public void setAfId(Integer afId) {
        this.afId = afId;
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

    public String getValidationMsg() {
        return validationMsg;
    }

    public void setValidationMsg(String validationMsg) {
        this.validationMsg = validationMsg;
    }

    public LocalDateTime getPutTime() {
        return putTime;
    }

    public void setPutTime(LocalDateTime putTime) {
        this.putTime = putTime;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
