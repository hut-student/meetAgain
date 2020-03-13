package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reply implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer cId; //评论id
    private Integer toFloor;
    private Integer fromUid;
    private Integer toUid;
    private String rComment;
    private LocalDateTime rTime;
    private Integer rFloor;

    public Reply() {
    }

    public Reply(Integer cId, Integer toFloor, Integer fromUid, Integer toUid, String rComment, LocalDateTime rTime) {
        this.cId = cId;
        this.toFloor = toFloor;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.rComment = rComment;
        this.rTime = rTime;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", cId=" + cId +
                ", toFloor=" + toFloor +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", rComment='" + rComment + '\'' +
                ", rTime=" + rTime +
                ", rFloor=" + rFloor +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getToFloor() {
        return toFloor;
    }

    public void setToFloor(Integer toFloor) {
        this.toFloor = toFloor;
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
        this.rTime = rTime;
    }

    public Integer getrFloor() {
        return rFloor;
    }

    public void setrFloor(Integer rFloor) {
        this.rFloor = rFloor;
    }
}
