package com.vo;

public class ResponseBean {

    private long code;
    private String msg;
    private final long time = System.currentTimeMillis();
    private String enCode;
    private Object data;

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                ", enCode='" + enCode + '\'' +
                ", data=" + data +
                '}';
    }

    public ResponseBean() {
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    //需要加密参数的构造函数
    public ResponseBean(long code, String msg, String enCode, Object data) {
        this.code = code;
        this.msg = msg;
        this.enCode = enCode;
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public long getCode() {
        return code;
    }

    //无需加密参数的构造函数
    public ResponseBean(long code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

