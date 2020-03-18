package com.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class ImBeans implements Serializable {

    private String imId; //聊天imId array中信息的imId都等于他
    private ArrayList<ImMessageBean> array; //按照time生成序排序

    @Override
    public String toString() {
        return "ImBeans{" +
                "imId='" + imId + '\'' +
                ", array=" + array +
                '}';
    }

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    public ArrayList<ImMessageBean> getArray() {
        return array;
    }

    public void setArray(ArrayList<ImMessageBean> array) {
        this.array = array;
    }
}
