package com.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.UserDAO;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserDAO, User> {

    @Autowired
    private UserDAO userDAO;

    public List findAllUser(){
        return userDAO.findAllUser();
    }

    /**
     * 通过电话登陆
     * @param tele 电话号码
     * @return
     */
    public User teleLoginUser(String tele, String pw){
        return userDAO.TelePwLogin(tele, pw);
    }




}
