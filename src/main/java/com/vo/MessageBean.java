package com.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageBean implements Serializable {

    private Integer type; //0：心跳机制  1：单聊消息（单个）  2：单聊消息（多个）  3：系统消息（单个） 4：系统消息（多个）
    private ImMessageBean imMessage; //type=1时，只有单个聊天消息
    private SystemMessageBean systemMessage; //type=3时
    private ArrayList<ImBeans> imBeansArray; //type=2时，有多个聊天消息
    private ArrayList<SystemMessageBean> systemMessageArray;   //type=4时，有多个系统消息
    private String msg;

    @Override
    public String toString() {
        return "MessageBean{" +
                "type=" + type +
                ", imMessage=" + imMessage +
                ", systemMessage=" + systemMessage +
                ", imBeansArray=" + imBeansArray +
                ", systemMessageArray=" + systemMessageArray +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Integer getType() {
        return type;
    }

    public ArrayList<ImBeans> getImBeansArray() {
        return imBeansArray;
    }

    public void setImBeansArray(ArrayList<ImBeans> imBeansArray) {
        this.imBeansArray = imBeansArray;
    }

    public ArrayList<SystemMessageBean> getSystemMessageArray() {
        return systemMessageArray;
    }

    public void setSystemMessageArray(ArrayList<SystemMessageBean> systemMessageArray) {
        this.systemMessageArray = systemMessageArray;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ImMessageBean getImMessage() {
        return imMessage;
    }

    public void setImMessage(ImMessageBean imMessage) {
        this.imMessage = imMessage;
    }

    public SystemMessageBean getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(SystemMessageBean systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
