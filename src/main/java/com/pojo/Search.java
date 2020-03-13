package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    @TableField("s_age")
    private Integer age;    //年龄


    @TableField(exist = false)
    private String sendTime; //发布时间 自动填充
    @TableField(exist = false)
    private String time; //走失时间 自动填充
    @TableField(exist = false)
    private List<String> pictures;  //图片的路径集合
    @TableField(exist = false)
    private String header;
    @TableField(exist = false)
    private String nickName;
    @TableField(exist = false)
    private Integer commentNum;

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
                ", age=" + age +
                ", sendTime='" + sendTime + '\'' +
                ", time='" + time + '\'' +
                ", pictures=" + pictures +
                ", header='" + header + '\'' +
                ", nickName='" + nickName + '\'' +
                ", commentNum=" + commentNum +
                '}';
    }

    public List<String> getPictures() {
        return pictures;
    }

    public String getsDistrict() {
        return sDistrict;
    }

    public void setsDistrict(String sDistrict) {
        this.sDistrict = sDistrict;
    }

    public String getHeader() {
        return header;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getsLostTime() {
        return sLostTime;
    }

    public void setsLostTime(LocalDateTime sLostTime) {
        this.time = sLostTime.getYear() + "-" + sLostTime.getMonthValue() + "-" + sLostTime.getDayOfMonth();
        this.sLostTime = sLostTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
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
        this.sendTime = sTime.getYear() + "-" + sTime.getMonthValue() + "-" + sTime.getDayOfMonth();
        this.sTime = sTime;
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
