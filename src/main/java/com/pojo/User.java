package com.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

    @TableId(value = "u_id", type = IdType.AUTO)
    private int uId;
    private String uUuid;           //用户号
    private String uName;           //真实姓名
    private String uRealName;       //用户昵称
    private String uPw;             //密码
    private Integer uSex;               //性别
    private String uBirthday;    //出生年月
    private String uHeadPortrait;   //头像
    private String uTele;           //电话
    private String uProvince;       //省份
    private String uCity;           //市
    private String uDistrict;       //区
    private String uAddress;        //详细地址
    private String uQq;             //QQ登陆字段
    @TableField("u_wechat")
    private String uWeChat;         //微信登陆
    private String uMicroblog;      //微博登陆
    private Integer uVolunteer;         //是否志愿者
    private LocalDateTime uUpdateTime;  //更新时间
    private String uSignature;      //个性签名

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", uUuid='" + uUuid + '\'' +
                ", uName='" + uName + '\'' +
                ", uRealName='" + uRealName + '\'' +
                ", uPw='" + uPw + '\'' +
                ", uSex=" + uSex +
                ", uBirthday='" + uBirthday + '\'' +
                ", uHeadPortrait='" + uHeadPortrait + '\'' +
                ", uTele='" + uTele + '\'' +
                ", uProvince='" + uProvince + '\'' +
                ", uCity='" + uCity + '\'' +
                ", uDistrict='" + uDistrict + '\'' +
                ", uAddress='" + uAddress + '\'' +
                ", uQq='" + uQq + '\'' +
                ", uWeChat='" + uWeChat + '\'' +
                ", uMicroblog='" + uMicroblog + '\'' +
                ", uVolunteer=" + uVolunteer +
                ", uUpdateTime=" + uUpdateTime +
                ", uSignature='" + uSignature + '\'' +
                '}';
    }

    public String getuUuid() {
        return uUuid;
    }

    public void setuUuid(String uUuid) {
        this.uUuid = uUuid;
    }

    public String getuRealName() {
        return uRealName;
    }

    public void setuRealName(String uRealName) {
        this.uRealName = uRealName;
    }

    public String getuSignature() {
        return uSignature;
    }

    public void setuSignature(String uSignature) {
        this.uSignature = uSignature;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPw() {
        return uPw;
    }

    public void setuPw(String uPw) {
        this.uPw = uPw;
    }

    public Integer getuSex() {
        return uSex;
    }

    public void setuSex(Integer uSex) {
        this.uSex = uSex;
    }

    public String getuBirthday() {
        return uBirthday;
    }

    public void setuBirthday(String uBirthday) {
        this.uBirthday = uBirthday;
    }

    public String getuHeadPortrait() {
        return uHeadPortrait;
    }

    public void setuHeadPortrait(String uHeadPortrait) {
        this.uHeadPortrait = uHeadPortrait;
    }

    public String getuTele() {
        return uTele;
    }

    public void setuTele(String uTele) {
        this.uTele = uTele;
    }

    public String getuProvince() {
        return uProvince;
    }

    public void setuProvince(String uProvince) {
        this.uProvince = uProvince;
    }

    public String getuCity() {
        return uCity;
    }

    public void setuCity(String uCity) {
        this.uCity = uCity;
    }

    public String getuDistrict() {
        return uDistrict;
    }

    public void setuDistrict(String uDistrict) {
        this.uDistrict = uDistrict;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public String getuQq() {
        return uQq;
    }

    public void setuQq(String uQq) {
        this.uQq = uQq;
    }

    public String getuWeChat() {
        return uWeChat;
    }

    public void setuWeChat(String uWeChat) {
        this.uWeChat = uWeChat;
    }

    public String getuMicroblog() {
        return uMicroblog;
    }

    public void setuMicroblog(String uMicroblog) {
        this.uMicroblog = uMicroblog;
    }

    public Integer getuVolunteer() {
        return uVolunteer;
    }

    public void setuVolunteer(Integer uVolunteer) {
        this.uVolunteer = uVolunteer;
    }

    public LocalDateTime getuUpdateTime() {
        return uUpdateTime;
    }

    public void setuUpdateTime(LocalDateTime uUpdateTime) {
        this.uUpdateTime = uUpdateTime;
    }
}
