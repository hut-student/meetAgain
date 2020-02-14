package com.action;

import com.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("search")
@CrossOrigin("*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //添加一条寻找消息
    @RequestMapping("add")
    public void addSearch(){
        searchService.addSearch();
    }

}
