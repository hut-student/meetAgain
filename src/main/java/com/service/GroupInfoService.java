package com.service;

import com.dao.GroupInfoDAO;
import com.pojo.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupInfoService {

    @Autowired
    private GroupInfoDAO groupInfoDAO;
}
