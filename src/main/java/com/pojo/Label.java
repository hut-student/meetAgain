package com.pojo;

import java.io.Serializable;

public class Label implements Serializable {
    private int lId;
    private String lName;

    public int getlId() {
        return lId;
    }

    public void setlId(int lId) {
        this.lId = lId;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
