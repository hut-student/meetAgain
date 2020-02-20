package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Search implements Serializable {

    @TableId(value = "s_id", type = IdType.AUTO)
    private int sId;

    private Integer uId;
    private Integer sType;
    private String sTitle;
    private String sPhoto;
    private Integer lId;
    private Integer sPageView;  //阅读量
    private String sProvince;   //省
    private String sCity;
    private String sDistrict;
    private String sAddress;

    @TableField("s_jw1")
    private String sJW1;    //地点的经纬度
    @TableField("s_jw2")
    private String sJW2;
    @TableField("s_jw3")
    private String sJW3;
    private LocalDateTime sLostTime; //走失时间
    private LocalDateTime sTime;    //发布时间
    private String sContent;    //发布内容
    private Integer sPriority;      //优先级

    @TableField(exist = false)
    private Long time;
    @TableField(exist = false)
    private Long lostTime;

    @Override
    public String toString() {
        return "Search{" +
                "sId=" + sId +
                ", uId=" + uId +
                ", sType=" + sType +
                ", sTitle='" + sTitle + '\'' +
                ", sPhoto='" + sPhoto + '\'' +
                ", lId=" + lId +
                ", sPageView=" + sPageView +
                ", sProvince='" + sProvince + '\'' +
                ", sCity='" + sCity + '\'' +
                ", sDistrict='" + sDistrict + '\'' +
                ", sAddress='" + sAddress + '\'' +
                ", sJW1='" + sJW1 + '\'' +
                ", sJW2='" + sJW2 + '\'' +
                ", sJW3='" + sJW3 + '\'' +
                ", sLostTime=" + sLostTime +
                ", sTime=" + sTime +
                ", sContent='" + sContent + '\'' +
                ", sPriority=" + sPriority +
                ", time=" + time +
                ", lostTime=" + lostTime +
                '}';
    }

    public String getsDistrict() {
        return sDistrict;
    }

    public void setsDistrict(String sDistrict) {
        this.sDistrict = sDistrict;
    }

    public Long getLostTime() {
        return lostTime;
    }

    public void setLostTime(Long lostTime) {
        this.lostTime = lostTime;
    }

    public LocalDateTime getsLostTime() {
        return sLostTime;
    }

    public void setsLostTime(LocalDateTime sLostTime) {
        this.sLostTime = sLostTime;
        this.lostTime = MyMiniUtils.timeMillisChangeLocalDateTime(sLostTime);
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getsType() {
        return sType;
    }

    public void setsType(Integer sType) {
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

    public Integer getsPageView() {
        return sPageView;
    }

    public void setsPageView(Integer sPageView) {
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
        this.time = MyMiniUtils.timeMillisChangeLocalDateTime(sTime);
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public Integer getsPriority() {
        return sPriority;
    }

    public void setsPriority(Integer sPriority) {
        this.sPriority = sPriority;
    }
}
