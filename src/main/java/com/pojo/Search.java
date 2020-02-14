package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Search implements Serializable {

    @TableId(value = "s_id", type = IdType.AUTO)
    private int sId;

    private int uId;
    private int sType;
    private String sTitle;
    private String sPhoto;
    private Integer lId;
    private int sPageView;  //阅读量
    private String sProvince;   //省
    private String sCity;
    private String sDistict;
    private String sAddress;

    @TableField("s_jw1")
    private String sJW1;    //地点的经纬度
    @TableField("s_jw2")
    private String sJW2;
    @TableField("s_jw3")
    private String sJW3;
    private LocalDateTime sTime;
    private String sContent;    //发布内容
    private int sPriority;      //优先级

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getsType() {
        return sType;
    }

    public void setsType(int sType) {
        this.sType = sType;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsPhoto() {
        return sPhoto;
    }

    public void setsPhoto(String sPhoto) {
        this.sPhoto = sPhoto;
    }

    public Integer getlId() {
        return lId;
    }

    public void setlId(Integer lId) {
        this.lId = lId;
    }

    public int getsPageView() {
        return sPageView;
    }

    public void setsPageView(int sPageView) {
        this.sPageView = sPageView;
    }

    public String getsProvince() {
        return sProvince;
    }

    public void setsProvince(String sProvince) {
        this.sProvince = sProvince;
    }

    public String getsCity() {
        return sCity;
    }

    public void setsCity(String sCity) {
        this.sCity = sCity;
    }

    public String getsDistict() {
        return sDistict;
    }

    public void setsDistict(String sDistict) {
        this.sDistict = sDistict;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsJW1() {
        return sJW1;
    }

    public void setsJW1(String sJW1) {
        this.sJW1 = sJW1;
    }

    public String getsJW2() {
        return sJW2;
    }

    public void setsJW2(String sJW2) {
        this.sJW2 = sJW2;
    }

    public String getsJW3() {
        return sJW3;
    }

    public void setsJW3(String sJW3) {
        this.sJW3 = sJW3;
    }

    public LocalDateTime getsTime() {
        return sTime;
    }

    public void setsTime(LocalDateTime sTime) {
        this.sTime = sTime;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public int getsPriority() {
        return sPriority;
    }

    public void setsPriority(int sPriority) {
        this.sPriority = sPriority;
    }
}
