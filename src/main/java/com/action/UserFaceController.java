package com.action;

import com.service.UserFaceService;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("face")
public class UserFaceController {

    @Autowired
    private UserFaceService userFaceService;

    //创建一个人员，上传一张人脸。添加人脸
    @RequestMapping("addFace")
    public ResponseBean uploadFacePhoto(Integer uId, MultipartFile file){
        int check = userFaceService.peopleCheck(uId);
        System.out.println(check);
        System.out.println("file" + file);
        if(check == 1){
            //表示用户已经创建了人员
            MultipartFile[] files = new MultipartFile[1];
            files[0] = file;
            return userFaceService.addUserFace(uId, files);
        }else if(check == -1){
            //用户需要创建人员
            return userFaceService.insertUserFace(uId, file);
        }else{
            return new ResponseBean(402,"用户上传人脸已达上限",null);
        }
    }

    /*@RequestMapping("addFace")
    public ResponseBean selectUserFace(Integer uId, MultipartFile file){
        MultipartFile[] files = new MultipartFile[1];
        files[0] = file;
        return userFaceService.addUserFace(uId, files);
    }*/

    //删除人脸id
    @RequestMapping("deleteFace")
    public ResponseBean deleteUserFace(Integer uId, String faceIdListJson){
        return userFaceService.deleteFace(uId, faceIdListJson);
    }

    //搜索人脸
    @RequestMapping("searchFace")
    public ResponseBean SearchFaces(Integer uId, MultipartFile file){
        return userFaceService.SearchFaces(file);
    }

    //用户id集合找用户的上传图片
    @RequestMapping("selectUserId")
    public ResponseBean selectUserList(Integer userId){
        return userFaceService.findUserFaces(userId);
    }
}
