package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class UserFace implements Serializable {

    @TableId
    private String faceId;
    private Integer uid;
    private String url;

    @TableField(exist = false)
    private Float probability;
    @TableField(exist = false)
    private String header;
    @TableField(exist = false)
    private String nickName;
    @TableField(exist = false)
    private String introduce;

    public UserFace() {
    }

    public UserFace(String faceId, Integer uid, String url) {
        this.faceId = faceId;
        this.uid = uid;
        this.url = url;
    }

    @Override
    public String toString() {
        return "UserFace{" +
                "faceId='" + faceId + '\'' +
                ", uid=" + uid +
                ", url='" + url + '\'' +
                ", header='" + header + '\'' +
                ", nickName='" + nickName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", probability=" + probability +
                '}';
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }
}
