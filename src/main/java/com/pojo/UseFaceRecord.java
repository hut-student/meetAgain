package com.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UseFaceRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer useUserId;
    private Integer saoUserId;
    private LocalDateTime time;
    private Integer sum;

    @Override
    public String toString() {
        return "UseFaceRecode{" +
                "id=" + id +
                ", useUserId=" + useUserId +
                ", saoUserId=" + saoUserId +
                ", time=" + time +
                ", sum=" + sum +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUseUserId() {
        return useUserId;
    }

    public void setUseUserId(Integer useUserId) {
        this.useUserId = useUserId;
    }

    public Integer getSaoUserId() {
        return saoUserId;
    }

    public void setSaoUserId(Integer saoUserId) {
        this.saoUserId = saoUserId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
