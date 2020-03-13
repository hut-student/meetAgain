package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.FaceGroupUserDAO;
import com.pojo.FaceGroupUser;
import org.springframework.stereotype.Service;

@Service
public class FaceGroupUserService extends ServiceImpl<FaceGroupUserDAO, FaceGroupUser> {
}
