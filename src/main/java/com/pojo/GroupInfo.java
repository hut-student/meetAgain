package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GroupInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer giId;
    private String giUuid;
    private String giNickName;
    private Integer giMasterId;
    private String giPhoto;
    private String giSignature;
    private LocalDateTime giCreateTime;

    @TableField(exist = false)
    private Long giTime;


    @Override
    public String toString() {
        return "GroupInfo{" +
                "giId=" + giId +
                ", giUuid='" + giUuid + '\'' +
                ", giNickName='" + giNickName + '\'' +
                ", giMasterId=" + giMasterId +
                ", giPhoto='" + giPhoto + '\'' +
                ", giSignature='" + giSignature + '\'' +
                ", giCreateTime=" + giCreateTime +
                ", giTime=" + giTime +
                '}';
    }

    public String getGiUuid() {
        return giUuid;
    }

    public void setGiUuid(String giUuid) {
        this.giUuid = giUuid;
    }

    public Integer getGiId() {
        return giId;
    }

    public void setGiId(Integer giId) {
        this.giId = giId;
    }

    public String getGiNickName() {
        return giNickName;
    }

    public void setGiNickName(String giNickName) {
        this.giNickName = giNickName;
    }

    public Integer getGiMasterId() {
        return giMasterId;
    }

    public void setGiMasterId(Integer giMasterId) {
        this.giMasterId = giMasterId;
    }

    public String getGiPhoto() {
        return giPhoto;
    }

    public void setGiPhoto(String giPhoto) {
        this.giPhoto = giPhoto;
    }

    public String getGiSignature() {
        return giSignature;
    }

    public void setGiSignature(String giSignature) {
        this.giSignature = giSignature;
    }



    public LocalDateTime getGiCreateTime() {
        return giCreateTime;
    }

    public void setGiCreateTime(LocalDateTime giCreateTime) {
        this.giTime = MyMiniUtils.timeMillisChangeLocalDateTime(giCreateTime);
        this.giCreateTime = giCreateTime;
    }

    public Long getGiTime() {
        return giTime;
    }

    public void setGiTime(Long giTime) {
        this.giTime = giTime;
    }
}
