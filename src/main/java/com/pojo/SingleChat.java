package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.utils.MyMiniUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SingleChat implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer scId;
    private Integer fromUid;
    private Integer toUid;
    private String scContent;
    private LocalDateTime scTime;

    @TableField(exist = false)
    private Long putTime;


    public SingleChat() {
    }

    public SingleChat(Integer fromUid, Integer toUid, String scContent, LocalDateTime scTime) {
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.scContent = scContent;
        this.scTime = scTime;
    }

    @Override
    public String toString() {
        return "SingleChat{" +
                "scId=" + scId +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", scContent='" + scContent + '\'' +
                ", scTime=" + scTime +
                ", putTime=" + putTime +
                '}';
    }

    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

    public String getScContent() {
        return scContent;
    }

    public void setScContent(String scContent) {
        this.scContent = scContent;
    }

    public LocalDateTime getScTime() {
        return scTime;
    }

    public void setScTime(LocalDateTime scTime) {
        this.putTime = MyMiniUtils.timeMillisChangeLocalDateTime(scTime);
        this.scTime = scTime;
    }

    public Long getPutTime() {
        return putTime;
    }

    public void setPutTime(Long putTime) {
        this.putTime = putTime;
    }
}
