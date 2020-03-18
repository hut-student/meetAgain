package com.action;

import com.service.MeetAgainSpaceService;
import com.tencentcloudapi.gme.v20180711.models.RealtimeSpeechConf;
import com.vo.ResponseBean;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.NumberUp;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("community")
public class MeetAgainSpaceController {

    @Autowired
    private MeetAgainSpaceService meetAgainSpaceService;

    /**
     * 发布重逢圈
     * @param uId
     * @param locate
     * @param content
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("sendMain")
    public ResponseBean addMeetAgainSpace(String uId, String locate, String content, Integer file, HttpServletRequest request){
        try {
            if(locate != null){
                return meetAgainSpaceService.addMeetAgainSpace(uId, locate, content, file, request);
            }else {
                return meetAgainSpaceService.addMeetAgainSpace(uId, null, content, file, request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(400,e.getMessage(),null);
        }
    }

    /**
     * 发布重逢圈 评论
     * @param communityId
     * @param uId
     * @param toUId
     * @param comment
     * @return
     */
    @RequestMapping("sendComment")
    public ResponseBean addComment(String communityId, String uId, String toUId, String comment){
        if(toUId != null){
            return meetAgainSpaceService.addComment(communityId, uId, toUId, comment);
        }else {
            return meetAgainSpaceService.addComment(communityId, uId, null, comment);
        }
    }

    /**
     * 获取重逢圈 关注用户发布的内容
     * @param uId
     * @param page
     * @return
     */
    @RequestMapping("obtainFollow")
    public ResponseBean findRelationshipMASpace(String uId, Integer page){
        return meetAgainSpaceService.findRelationshipMASpace(uId, page);
    }

    /**
     * 获取重逢圈 推荐(按热度)内容
     * @param page
     * @return
     */
    @RequestMapping("obtainRecommend")
    public ResponseBean findMASpace(Integer page){
        return meetAgainSpaceService.findMASpace(page);
    }

    /**
     *  获取重逢圈 某个用户的发布内容
     * @param uId
     * @return
     */
    @RequestMapping("obtainUser")
    public ResponseBean findOneMASpace(String uId){
        return meetAgainSpaceService.findOneMASpace(uId);
    }

    /**
     *  获取重逢圈 获取评论内容
     * @param communityId
     * @return
     */
    @RequestMapping("obtainComment")
    public ResponseBean findComment(String communityId, Integer page){

    }
}
