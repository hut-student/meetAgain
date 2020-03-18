package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.util.List;

public class MeetAgainSpace implements Serializable {

/*    var communityId:String? = null // 主题的 id 主键
    var header:String? = null // 头像URL
    var nickName:String? = null // 昵称
    var uId:String? = null // UID
    var time:Long = 0L // 时间 ->System.currentTimeMillis()

    var content:String? = null // 内容文字
    var locate:String? = null // 位置信息 [省市]
    var images:ArrayList<String>? = null // 内容图片URL
    var commentNum = 0 //评论总数
    *//**
     * 首次获取主题时候默认会包含主题的 1 - 3 楼的评论
     * 没有评论，则为null
     *//*
    var comments:ArrayList<Comment>? = null*/

    @TableId(value = "cf_id", type = IdType.AUTO)
    private Integer communityId;

    private Integer uId;
    @TableField(exist = false)
    private String header;
    @TableField(exist = false)
    private String nickName;

    private String cfTime;  //需要重新转换 ,自动转换
    @TableField(exist = false)
    private Long time;

    @TableField(value = "cf_content")
    private String content;
    @TableField(value = "cf_locate")
    private String locate;

    private String cfPhoto;
    @TableField(exist = false)
    private List<String> images;

    @TableField(exist = false)
    private int commentNum;     //评论总数
    /**
     * 首次获取主题时候默认会包含主题的 1 - 3 楼的评论
     * 没有评论，则为null
     */
    private List<MeetAgainSpaceComment> comments;

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getHeader() {
        return header;
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

    public String getCfTime() {
        return cfTime;
    }

    public void setCfTime(String cfTime) {
        this.time = Long.valueOf(cfTime);
        this.cfTime = cfTime;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.cfTime = time.toString();
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getCfPhoto() {
        return cfPhoto;
    }

    public void setCfPhoto(String cfPhoto) {
        this.cfPhoto = cfPhoto;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public List<MeetAgainSpaceComment> getComments() {
        return comments;
    }

    public void setComments(List<MeetAgainSpaceComment> comments) {
        this.comments = comments;
    }
}
