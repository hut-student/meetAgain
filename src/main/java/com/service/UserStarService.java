package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.SearchDAO;
import com.dao.UserDAO;
import com.dao.UserStarDAO;
import com.pojo.Search;
import com.pojo.User;
import com.pojo.UserStar;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserStarService {

    @Autowired
    private UserStarDAO userStarDAO;

    @Autowired
    private SearchDAO searchDAO;

    /**
     * 查询目标用户收藏的所有寻人寻物
     * @param uId
     * @param page
     * @return
     */
    public ResponseBean findUserStarSearch(Integer uId, Integer page){
        try {
            Page<UserStar> p = new Page<>(page, 10);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("u_id", uId);
            IPage<UserStar> iPage = userStarDAO.selectPage(p, queryWrapper);
            if(iPage.getRecords() == null || iPage.getRecords().size() == 0){
                return new ResponseBean(300, "list为空", null);
            }
            List<Search> searchList = new ArrayList<>();
            for(UserStar userStar : iPage.getRecords()){
                searchList.add(searchDAO.selectById(userStar.getsId()));
            }
            return new ResponseBean(200,"查询成功", searchList);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseBean(400, e.getMessage(), null);
        }
    }


    //查询用户是否收藏某个 寻人/寻物 帖子
    public ResponseBean checkUserStar(Integer uId, Integer sId){
        try {
            QueryWrapper queryWrapper =  new QueryWrapper();
            queryWrapper.eq("u_id", uId);
            queryWrapper.eq("s_id", sId);
            if(userStarDAO.selectOne(queryWrapper) != null){
                return new ResponseBean(200, "查询成功", true);
            }else {
                return new ResponseBean(200, "查询成功", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400,e.getMessage(), null);
        }
    }

}
