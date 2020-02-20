package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FeedBack implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer fId;
    private Integer uId;
    private String fContent;
    private String fPhoto;
    private String fTele;
    private LocalDateTime fTime;

    @TableField(exist = false)
    private Long fTimeStamp;


    @Override
    public String toString() {
        return "FeedBack{" +
                "fId=" + fId +
                ", uId=" + uId +
                ", fContent='" + fContent + '\'' +
                ", fPhoto='" + fPhoto + '\'' +
                ", fTele='" + fTele + '\'' +
                ", fTime=" + fTime +
                ", fTimeStamp=" + fTimeStamp +
                '}';
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
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
        this.fTimeStamp = MyMiniUtils.timeMillisChangeLocalDateTime(fTime);
    }

    public Long getfTimeStamp() {
        return fTimeStamp;
    }

    public void setfTimeStamp(Long fTimeStamp) {
        this.fTimeStamp = fTimeStamp;
    }
}
