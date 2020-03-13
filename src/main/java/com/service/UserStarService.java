package com.service;

import com.dao.UserDAO;
import com.dao.UserStarDAO;
import com.pojo.User;
import com.pojo.UserStar;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStarService {

    @Autowired
    private UserStarDAO userStarDAO;

    @Autowired
    private UserDAO userDAO;

/*    public ResponseBean userStarChange(Integer uId, Integer sId, Integer star){
    }*/
}
