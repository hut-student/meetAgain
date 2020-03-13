package com.action;

import com.pojo.UserRelationship;
import com.service.UserRelationshipService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("home")
public class HomeController {

    @Autowired
    private UserRelationshipService userRelationshipService;

    //根据用户的uid获取他关注的人发表的说说，以发布时间为顺序，内容混合寻人和寻物
    @RequestMapping("follow")
    public ResponseBean findFriendsSearch(Integer uId, Integer page) {
        return userRelationshipService.findFriendsSearch(uId, page, 5);
    }

    //按照热度获取说说，内容混合寻人和寻物
    @RequestMapping("recommend")
    public ResponseBean findHotSearch(Integer page) {
        return userRelationshipService.findHotSearch(page, 10);
    }

    //按照用户当前定位获取说说，内容混合寻人和寻物
    @RequestMapping("nearby")
    public ResponseBean findDirSearch(String locate, Integer page) {
        return userRelationshipService.findDirSearch(locate, page, 10);
    }

    //按照发布时间顺序获取寻人说说
    @RequestMapping("body")
    public ResponseBean findSearchPeople(@RequestParam(defaultValue = "abc") String time, @RequestParam(defaultValue = "abc") String province, @RequestParam(defaultValue = "abc") String city, @RequestParam(defaultValue = "abc") String district, Integer page) {
        return userRelationshipService.findSearchPeople(time, null, province, city, district, page);
    }


    @RequestMapping("people")
    public ResponseBean findSearchPeople(@RequestParam(defaultValue = "abc") String time, @RequestParam(defaultValue = "abc") String age, @RequestParam(defaultValue = "abc") String province, @RequestParam(defaultValue = "abc") String city, @RequestParam(defaultValue = "abc") String district, Integer page) {
        return userRelationshipService.findSearchPeople(time, age, province, city, district, page);
    }


}
