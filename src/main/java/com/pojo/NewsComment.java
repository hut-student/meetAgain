package com.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.utils.MyMiniUtils;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("xw_comments")
public class NewsComment implements Serializable {

    @TableId
    private int xwcId;
    private int uId;
    private String xwId;
    private String xwcComment;
    private LocalDateTime xwcTime;
    private int xwcFloor;

    @TableField(exist = false)
    private Long time;
    @TableField(exist = false)
    private String uName;
    @TableField(exist = false)
    private String uHeadPortrait;
    @TableField(exist = false)
    private Integer commentSum;
    @TableField(exist = false)
    private int replySum;

    public NewsComment(int xwcId, int uId, String xwId, String xwcComment, int xwcFloor, Long time, String uName, String uHeadPortrait, int replySum) {
        this.xwcId = xwcId;
        this.uId = uId;
        this.xwId = xwId;
        this.xwcComment = xwcComment;
        this.xwcFloor = xwcFloor;
        this.time = time;
        this.uName = uName;
        this.uHeadPortrait = uHeadPortrait;
        this.replySum = replySum;
    }

    @Override
    public String toString() {
        return "NewsComment{" +
                "xwcId=" + xwcId +
                ", uId=" + uId +
                ", xwId='" + xwId + '\'' +
                ", xwcComment='" + xwcComment + '\'' +
                ", xwcTime=" + xwcTime +
                ", xwcFloor=" + xwcFloor +
                ", time=" + time +
                ", uName='" + uName + '\'' +
                ", uHeadPortrait='" + uHeadPortrait + '\'' +
                ", replySum=" + replySum +
                '}';
    }

    public NewsComment() {
    }

    public NewsComment(int uId, String xwId, String xwcComment, LocalDateTime xwcTime) {
        this.uId = uId;
        this.xwId = xwId;
        this.xwcComment = xwcComment;
        this.xwcTime = xwcTime;
    }

    public Integer getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(Integer commentSum) {
        this.commentSum = commentSum;
    }

    public int getReplySum() {
        return replySum;
    }

    public void setReplySum(int replySum) {
        this.replySum = replySum;
    }

    public String getuHeadPortrait() {
        return uHeadPortrait;
    }

    public void setuHeadPortrait(String uHeadPortrait) {
        this.uHeadPortrait = uHeadPortrait;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getXwcId() {
        return xwcId;
    }

    public void setXwcId(int xwcId) {
        this.xwcId = xwcId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getXwId() {
        return xwId;
    }

    public void setXwId(String xwId) {
        this.xwId = xwId;
    }

    public String getXwcComment() {
        return xwcComment;
    }

    public void setXwcComment(String xwcComment) {
        this.xwcComment = xwcComment;
    }

    public LocalDateTime getXwcTime() {
        return xwcTime;
    }

    public void setXwcTime(LocalDateTime xwcTime) {
        this.time = MyMiniUtils.timeMillisChangeLocalDateTime(xwcTime);
        this.xwcTime = xwcTime;
    }

    public int getXwcFloor() {
        return xwcFloor;
    }

    public void setXwcFloor(int xwcFloor) {
        this.xwcFloor = xwcFloor;
    }
}
