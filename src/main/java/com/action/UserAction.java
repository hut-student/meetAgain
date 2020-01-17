package com.action;

import com.pojo.User;
import com.service.UserService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserAction {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUser")
    public List<User> findAllUser(){
        return userService.findAllUser();
    }

    @RequestMapping("/teleLogin")
    public ResponseBean findOneUser(String tele, String password){
        User user = userService.teleLoginUser(tele, password);
        if(user != null){
            return new ResponseBean(200,0, user);
        }
        return new ResponseBean(500, 0, null);
    }
}
