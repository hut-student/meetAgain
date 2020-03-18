package com.action;

import com.service.UserRelationshipService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("friend")
public class FriendController {

    @Autowired
    private UserRelationshipService userRelationshipService;

    @RequestMapping("search")
    public ResponseBean findMutualConcern(Integer uid){
        try {
            return userRelationshipService.findMutualConcern(uid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400, e.getMessage(),null);
        }
    }
}
