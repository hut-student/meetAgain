package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.UserGroupsDAO;
import com.pojo.UserGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGroupsService {

    @Autowired
    private UserGroupsDAO userGroupsDAO;

    //找群里面有那些人
    public List<Integer> groupPeople(Integer giId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gi_id", giId);
        List<UserGroups> userGroupsList = userGroupsDAO.selectList(queryWrapper);
        List list = new ArrayList();
        for (UserGroups userGroups: userGroupsList){
            list.add(userGroups.getuId());
        }
        return list;
    }

}
