package com.action;

import com.service.SearchCommentService;
import com.service.SearchService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("search")
@CrossOrigin("*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchCommentService searchCommentService;

    //添加一条寻找消息
    @RequestMapping("add")
    public ResponseBean addSearch(String searchJson, HttpServletRequest request){
        return searchService.addSearch(searchJson, request);
    }

    //根据uid获取某个具体发表的寻人或者寻物
/*    @RequestMapping("user")
    public ResponseBean findUesrSearch(Integer type, Integer uId, Integer page){
        return
    }*/


    //修改消息
    @RequestMapping("update")
    public ResponseBean updateSearch(String searchJson){
        return searchService.updateSearch(searchJson);
    }

    //根据条件提取寻人信息
//    public ResponseBean selectSearch(){
//
//    }

    /**
     * 寻找评论
     * @param sId
     * @param page
     * @return
     */
    @RequestMapping("findComment")
    public ResponseBean findSearchComment(Integer sId, Integer page){
        return searchCommentService.findSearchComment(sId, page, 5);
    }

    /**
     * 寻找回复
     * @param cId
     * @param page
     * @return
     */
    @RequestMapping("findReply")
    public ResponseBean findSearchReply(Integer cId, Integer page){
        return searchCommentService.findReply(cId, page, 10);
    }

    /**
     * 添加一条评论
     * @param uId
     * @param sId
     * @param comment
     * @return
     */
    @RequestMapping("addComment")
    public ResponseBean addSearchComment(Integer uId, Integer sId, String comment){
        return searchCommentService.addSearchComment(uId, sId, comment);
    }

    /**
     * 添加一条评论
     * @param cId
     * @param toFloor
     * @param fromUid
     * @param toUid
     * @param comment
     * @return
     */
    @RequestMapping("addReply")
    public ResponseBean addSearchReply(Integer cId, Integer toFloor, Integer fromUid, Integer toUid, String comment){
        return searchCommentService.addOneReply(cId, toFloor, fromUid, toUid, comment);
    }

    /**
     * 获取用户发表历史
     * @param type
     * @param uId
     * @param page
     * @return
     */
    @RequestMapping("user")
    public ResponseBean findSearch(Integer type, Integer uId, Integer page){
        return searchService.findUserSearch(type, uId, page, 5);
    }

    /**
     * 收藏寻人或寻物的帖子
     * @param uId
     * @param sId
     * @param star
     * @return
     */
    @RequestMapping("star")
    public ResponseBean changeStart(Integer uId, Integer sId, Integer star){
        try {
            return searchService.changeStar(uId, sId, star);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400,e.getMessage(), null);
        }
    }


    /**
     * 对用户自己发表的寻人/寻物帖子进行删除
     * @param uId
     * @param sId
     * @return
     */
    public ResponseBean deleteSearch(Integer uId, Integer sId){
        return searchService.deleteSearch(uId, sId);
    }



}
