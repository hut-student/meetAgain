package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class MeetAgainSpaceComment implements Serializable {

    /*var communityId:String? = null // 主题的id
    var header:String? = null // 头像URL
    var nickName:String? = null // 昵称
    var uId:String? = null // UID
    var time:Long = 0L // 时间 ->System.currentTimeMillis()

    var cId:String? = null //  评论的id 主键
    var comment:String? = null // 评论内容

    var toUId:String? = null // 被评论用户的UID,如果为null表示评论的是主题
    var toName:String? = null // 被评论用户的昵称,如果为null表示评论的是主题
    var floor:Int = 1 //评论的楼层 从 1 开始计数 (重要)*/

    @TableId(value = "cfr_id", type = IdType.AUTO)
    private int cId;        //评论的id
    @TableField(value = "cf_id")
    private Integer communityId;    //主题的id


    @TableField(value = "from_uid")
    private Integer uId;
    @TableField(exist = false)
    private String header;
    @TableField(exist = false)
    private String nickName;

    private String cfrTime;
    @TableField(exist = false)
    private Long time;

    @TableField(value = "crf_comment")
    private String comment;

    @TableField(value = "to_uid")
    private Integer toUId;   //被评论用户的UID,如果为null表示评论的是主题
    @TableField(exist = false)
    private String toName;  //被评论用户的昵称,如果为null表示评论的是主题
    private Integer floor;  //评论的楼层 从 1 开始计数 (重要)

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

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

    public String getCfrTime() {
        return cfrTime;
    }

    public void setCfrTime(String cfrTime) {
        this.time = Long.valueOf(cfrTime);
        this.cfrTime = cfrTime;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.cfrTime = time.toString();
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getToUId() {
        return toUId;
    }

    public void setToUId(Integer toUId) {
        this.toUId = toUId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
