package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class FaceGroupUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer groupUserId;
    private String  groupId;
    private Integer uId;

    @Override
    public String toString() {
        return "FaceGroupUser{" +
                "groupUserId=" + groupUserId +
                ", groupId=" + groupId +
                ", uId=" + uId +
                '}';
    }

    public FaceGroupUser() {
    }

    public FaceGroupUser(String groupId, Integer uId) {
        this.groupId = groupId;
        this.uId = uId;
    }

    public Integer getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(Integer groupUserId) {
        this.groupUserId = groupUserId;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }
}
