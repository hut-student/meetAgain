package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer cId;    //评论Id
    private Integer uId;    //用户id
    private Integer sId;    //对应的哪一条信息
    private String cComment;    //评论的内容
    private Integer cFloor;
    private LocalDateTime cTime;    //评论时间

    public Comment() {
    }

    public Comment(Integer uId, Integer sId, String cComment, LocalDateTime cTime) {
        this.uId = uId;
        this.sId = sId;
        this.cComment = cComment;
        this.cTime = cTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cId=" + cId +
                ", uId=" + uId +
                ", sId=" + sId +
                ", cComment='" + cComment + '\'' +
                ", cFloor=" + cFloor +
                ", cTime=" + cTime +
                '}';
    }

    public Integer getcFloor() {
        return cFloor;
    }

    public void setcFloor(Integer cFloor) {
        this.cFloor = cFloor;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getcComment() {
        return cComment;
    }

    public void setcComment(String cComment) {
        this.cComment = cComment;
    }

    public LocalDateTime getcTime() {
        return cTime;
    }

    public void setcTime(LocalDateTime cTime) {
        this.cTime = cTime;
    }

}
