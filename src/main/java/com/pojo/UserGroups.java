package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class UserGroups implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uId;
    private Integer giId;

    @Override
    public String toString() {
        return "UserGroups{" +
                "id=" + id +
                ", uId=" + uId +
                ", giId=" + giId +
                '}';
    }

    public UserGroups() {
    }

    public UserGroups(Integer uId, Integer giId) {
        this.uId = uId;
        this.giId = giId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getGiId() {
        return giId;
    }

    public void setGiId(Integer giId) {
        this.giId = giId;
    }
}
