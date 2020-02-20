package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.SearchDAO;
import com.pojo.Search;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService extends ServiceImpl<SearchDAO, Search> {

    @Autowired
    private SearchDAO searchDAO;

    //寻人图片文件夹
    @Value("${upload.findPeople.dir}")
    private String findPeopleDir;

    //网站域名
    @Value("${web.site}")
    private String webSite;


    //添加一条记录
    public ResponseBean addSearch(String searchJson){
        Search search = JSON.parseObject(searchJson, Search.class);
        search.setsTime(LocalDateTime.now());
        int lockMark;
        synchronized (this){
            lockMark = searchDAO.insert(search);
        }
        if(lockMark == 1){
            //以添加事件
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(true,"s_time",search.getsTime()).eq(true,"u_id",search.getuId()).eq(true,"s_type",search.getsType());
            search = searchDAO.selectOne(queryWrapper);
            return new ResponseBean(200,"添加成功", search);
        }
        return new ResponseBean(201,"添加失败",null);

    }

    //上传寻人图片 多图片上传
    public ResponseBean uploadSearchPhoto(HttpServletRequest request){
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = params.getFiles("file");
        //数组装图片名
        List<String> imgs = new ArrayList<String>();
        String newName ;
        for (MultipartFile file : files
             ) {
                newName = MyMiniUtils.upLoadFile(file, findPeopleDir);
                if(newName == null){
                    return new ResponseBean(201,"图片上传失败,请重试",null);
                }
                imgs.add("https://" + webSite + findPeopleDir + newName);
        }
        return new ResponseBean(200,"图片上传成功",imgs);
    }

    //修改记录
    public ResponseBean updateSearch(String SearchJson){
        Search search = JSON.parseObject(SearchJson, Search.class);
        if(searchDAO.updateById(search) == 1){
            return new ResponseBean(200, "修改成功", searchDAO.selectById(search));
        }else {
            return new ResponseBean(201,"修改失败",null);
        }
    }

    //根据条件寻找寻人信息
//    public ResponseBean selectSearch(){
//
//
//    }


}
