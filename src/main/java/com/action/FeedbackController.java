package com.action;

import com.service.FeedBackService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("feedBack")
public class FeedbackController {

    @Autowired
    private FeedBackService feedBackService;

    /**
     * 添加一条反馈
     * @param uId
     * @param time
     * @param enCode
     * @param feedBackJson
     * @return
     */
    @RequestMapping("addOne")
    public ResponseBean add(Integer uId, Long time, String enCode, String feedBackJson){
            return feedBackService.insertFeedBack(uId, time, enCode, feedBackJson);
    }


    /**
     * 查找反馈
     * @param uId
     * @param time
     * @param enCode
     * @return
     */
    @RequestMapping("find")
    public ResponseBean select(Integer uId, Long time, String enCode){
        return feedBackService.select(uId, time, enCode);
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("uploadPhoto")
    public ResponseBean uploadPhoto(MultipartFile file){
        return feedBackService.uploadPhoto(file);
    }
}
