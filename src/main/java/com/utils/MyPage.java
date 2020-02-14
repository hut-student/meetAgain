package com.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public class MyPage<T> {
    private Long current;   //当前页
    private Long page;      //总页数
    private Long size;      //每页的大小
    private Long total;     //总条数
    private List<T> records; //当前页的记录

    @Override
    public String toString() {
        return "MyPage{" +
                "current=" + current +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", records=" + records +
                '}';
    }

    public MyPage<T> iPageToMyPage(IPage<T> iPage){
        this.current = iPage.getCurrent();
        this.page = iPage.getPages();
        this.size = iPage.getSize();
        this.total = iPage.getTotal();
        this.records = iPage.getRecords();
        return this;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
