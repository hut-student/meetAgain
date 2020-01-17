package com.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class User implements Serializable {

    @TableId("u_id")
    private int uId;
    private String uName;           //用户名
    private String uPw;             //密码
    private int uSex;               //性别
    private LocalDate uBirthday;    //出生年月
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
    private int uVolunteer;         //是否志愿者
    private LocalDateTime uUpdateTime;  //更新时间

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

    public int getuSex() {
        return uSex;
    }

    public void setuSex(int uSex) {
        this.uSex = uSex;
    }

    public LocalDate getuBirthday() {
        return uBirthday;
    }

    public void setuBirthday(LocalDate uBirthday) {
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

    public int getuVolunteer() {
        return uVolunteer;
    }

    public void setuVolunteer(int uVolunteer) {
        this.uVolunteer = uVolunteer;
    }

    public LocalDateTime getuUpdateTime() {
        return uUpdateTime;
    }

    public void setuUpdateTime(LocalDateTime uUpdateTime) {
        this.uUpdateTime = uUpdateTime;
    }
}
