package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FeedBack implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer fId; //id
    private String fContent; //内容
    private String fPhoto; //图片
    private String fTele; //电话号码
    private LocalDateTime fTime; // 时间


    @Override
    public String toString() {
        return "FeedBack{" +
                "fId=" + fId +
                ", fContent='" + fContent + '\'' +
                ", fPhoto='" + fPhoto + '\'' +
                ", fTele='" + fTele + '\'' +
                ", fTime=" + fTime +
                '}';
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }

    public String getfPhoto() {
        return fPhoto;
    }

    public void setfPhoto(String fPhoto) {
        this.fPhoto = fPhoto;
    }

    public String getfTele() {
        return fTele;
    }

    public void setfTele(String fTele) {
        this.fTele = fTele;
    }

    public LocalDateTime getfTime() {
        return fTime;
    }

    public void setfTime(LocalDateTime fTime) {
        this.fTime = fTime;
    }

}
