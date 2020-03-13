package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class FaceGroupInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer groupId;
    private String groupName;

    @Override
    public String toString() {
        return "FaceGroupInfo{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    public FaceGroupInfo() {
    }

    public FaceGroupInfo(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public FaceGroupInfo(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
