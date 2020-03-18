package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserRelationship implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer urId;
    private Integer myselfUid;
    private Integer himselfUid;
    private String ur_name;
    private Integer sum;


    @Override
    public String toString() {
        return "UserRelationship{" +
                "urId=" + urId +
                ", myselfUid=" + myselfUid +
                ", himselfUid=" + himselfUid +
                ", ur_name='" + ur_name + '\'' +
                ", sum=" + sum +
                '}';
    }

    public UserRelationship() {
    }

    public UserRelationship(Integer myselfUid, Integer himselfUid) {
        this.myselfUid = myselfUid;
        this.himselfUid = himselfUid;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getUrId() {
        return urId;
    }

    public void setUrId(Integer urId) {
        this.urId = urId;
    }

    public Integer getMyselfUid() {
        return myselfUid;
    }

    public void setMyselfUid(Integer myselfUid) {
        this.myselfUid = myselfUid;
    }

    public Integer getHimselfUid() {
        return himselfUid;
    }

    public void setHimselfUid(Integer himselfUid) {
        this.himselfUid = himselfUid;
    }

    public String getUr_name() {
        return ur_name;
    }

    public void setUr_name(String ur_name) {
        this.ur_name = ur_name;
    }
}
