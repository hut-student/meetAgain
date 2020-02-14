package com.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("xw_reply")
public class NewsReply implements Serializable {

    @TableId
    private int xwrId;
    private int xwcId;
    private int xwrFloor;
    private int fromUid;
    private int toUid;
    private String rComment;
    private LocalDateTime rTime;
    private int rFloor;

    @TableField(exist = false)
    private long time;
    @TableField(exist = false)
    private String fromUname;
    @TableField(exist = false)
    private String uHeadPortrait;
    @TableField(exist = false)
    private String toUname;

    public NewsReply() {
    }

    public NewsReply(int xwcId, int xwrFloor, int fromUid, int toUid, String rComment, LocalDateTime rTime) {
        this.xwcId = xwcId;
        this.xwrFloor = xwrFloor;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.rComment = rComment;
        this.rTime = rTime;
    }



    @Override
    public String toString() {
        return "NewsReply{" +
                "xwrId=" + xwrId +
                ", xwcId=" + xwcId +
                ", xwrFloor=" + xwrFloor +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", rComment='" + rComment + '\'' +
                ", rTime=" + rTime +
                ", rFloor=" + rFloor +
                ", time=" + time +
                ", fromUname='" + fromUname + '\'' +
                ", uHeadPortrait='" + uHeadPortrait + '\'' +
                ", toUname='" + toUname + '\'' +
                '}';
    }

    public String getuHeadPortrait() {
        return uHeadPortrait;
    }

    public void setuHeadPortrait(String uHeadPortrait) {
        this.uHeadPortrait = uHeadPortrait;
    }

    public String getFromUname() {
        return fromUname;
    }

    public void setFromUname(String fromUname) {
        this.fromUname = fromUname;
    }

    public String getToUname() {
        return toUname;
    }

    public void setToUname(String toUname) {
        this.toUname = toUname;
    }

    public int getXwrId() {
        return xwrId;
    }

    public void setXwrId(int xwrId) {
        this.xwrId = xwrId;
    }

    public int getXwcId() {
        return xwcId;
    }

    public void setXwcId(int xwcId) {
        this.xwcId = xwcId;
    }

    public int getXwrFloor() {
        return xwrFloor;
    }

    public void setXwrFloor(int xwrFloor) {
        this.xwrFloor = xwrFloor;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public String getrComment() {
        return rComment;
    }

    public void setrComment(String rComment) {
        this.rComment = rComment;
    }

    public LocalDateTime getrTime() {
        return rTime;
    }

    public void setrTime(LocalDateTime rTime) {
        this.time = MyMiniUtils.timeMillisChangeLocalDateTime(rTime);
        this.rTime = rTime;
    }

    public int getrFloor() {
        return rFloor;
    }

    public void setrFloor(int rFloor) {
        this.rFloor = rFloor;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
