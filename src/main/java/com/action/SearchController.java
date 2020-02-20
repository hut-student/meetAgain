package com.action;

import com.service.SearchService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("search")
@CrossOrigin("*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //添加一条寻找消息
    @RequestMapping("add")
    public ResponseBean addSearch(String searchJson){
        return searchService.addSearch(searchJson);
    }

    //上传图片
    @RequestMapping("uploadPhoto")
    public ResponseBean uploadFindPeoplePhoto(HttpServletRequest request){
        return searchService.uploadSearchPhoto(request);
    }

    //修改消息
    @RequestMapping("update")
    public ResponseBean updateSearch(String searchJson){
        return searchService.updateSearch(searchJson);
    }

    //根据条件提取寻人信息
//    public ResponseBean selectSearch(){
//
//    }


}
