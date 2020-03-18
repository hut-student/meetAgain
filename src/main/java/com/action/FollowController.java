package com.action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.UserDAO;
import com.pojo.UserRelationship;
import com.service.UserRelationshipService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;

@RestController
@RequestMapping("follow")
public class FollowController {

    @Autowired
    private UserRelationshipService userRelationshipService;

    @Autowired
    private UserDAO userDAO;

    //查询用户是否收关注了某个用户
    @RequestMapping("whether")
    public ResponseBean findRelationship(Integer uId, Integer targetUId){
        return userRelationshipService.findOneRelationship(uId, targetUId);
    }


    /**
     * 查询目标uid用户关注的所有用户
     * @param uId
     * @return
     */
    @RequestMapping("search")
    public ResponseBean findUserRelationship(Integer uId){
        return userRelationshipService.findUserRelationship(uId, 1);
    }
}
