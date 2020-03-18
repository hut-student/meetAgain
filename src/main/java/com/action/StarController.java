package com.action;

import com.pojo.UserStar;
import com.service.UserStarService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("star")
public class StarController {

    @Autowired
    private UserStarService userStarService;

    //查询目标用户收藏的所有寻人寻物
    @RequestMapping("search")
    public ResponseBean findUserStarSearch(Integer uid, Integer page){
        return userStarService.findUserStarSearch(uid, page);
    }

    //查询用户是否收藏某个 寻人/寻物 帖子
    @RequestMapping("whether")
    public ResponseBean checkUserStar(Integer sId, Integer uId){
        return userStarService.checkUserStar(uId, sId);
    }
}
