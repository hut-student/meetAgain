package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.SearchDAO;
import com.pojo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService extends ServiceImpl<SearchDAO, Search> {

    @Autowired
    private SearchDAO searchDAO;


    //添加一条记录
    public void addSearch(){
        Search search = new Search();
        search.setlId(2);
        search.setsTitle("...");
        searchDAO.insert(search);
    }

    //更新记录
    //public void

}
