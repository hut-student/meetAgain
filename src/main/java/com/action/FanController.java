package com.action;

import com.service.UserRelationshipService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fan")
public class FanController {

    @Autowired
    private UserRelationshipService userRelationshipService;

    /**
     * 查询关注目标uid用户的所有用户(被关注 查询)
     * @param uId
     * @return
     */
    @RequestMapping("search")
    public ResponseBean  followThisUserOfAllUserPublicInfo(Integer uId){
        return userRelationshipService.findUserRelationship(uId, 0);
    }
}
