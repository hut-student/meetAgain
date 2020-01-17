package com.vo;

public class ResponseBean {

    private long code;
    private long count;
    private Object data;

    public long getCode() {
        return code;
    }

    public ResponseBean(long code, long count, Object data) {
        this.code = code;
        this.count = count;
        this.data = data;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
