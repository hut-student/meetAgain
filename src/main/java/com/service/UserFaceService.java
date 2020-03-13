package com.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dao.FaceGroupInfoDAO;
import com.dao.FaceGroupUserDAO;
import com.dao.UserDAO;
import com.dao.UserFaceDAO;
import com.google.gson.annotations.JsonAdapter;
import com.pojo.FaceGroupInfo;
import com.pojo.FaceGroupUser;
import com.pojo.User;
import com.pojo.UserFace;
import com.tencentcloudapi.iai.v20180301.models.*;
import com.utils.MyMiniUtils;
import com.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserFaceService {

    @Autowired
    private UserFaceUtils userFaceUtils;

    @Autowired
    private FaceGroupUserDAO faceGroupUserDAO;

    @Autowired
    private FaceGroupInfoDAO faceGroupInfoDAO;

    @Autowired
    private UserFaceDAO userFaceDAO;

    @Autowired
    private UserDAO userDAO;

    @Value("${web.site}")
    private String website;

    @Value("${upload.headPortrait.dir}")
    private String headPhoto;

    @Value("${upload.userFace.dir}")
    private String userFaceDir;

    //判断这个人是否已经注册了人员,并且是否达到了上限（五张）
    public int peopleCheck(Integer uId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("u_id", uId);
        if (faceGroupUserDAO.selectOne(queryWrapper) == null) {
            return -1; //未创建用户
        }
        QueryWrapper queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("uid", uId);
        if (userFaceDAO.selectList(queryWrapper1).size() == 5) {
            return 0; //已创建用户，但是已经有五张
        }
        return 1;//已创建用户，人脸未到达五张
    }


    //进行人员库检查
    public String groupCheck() {
        Integer maxGroupId = faceGroupInfoDAO.selectMaxId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("group_id", maxGroupId);
        if (faceGroupUserDAO.selectCount(queryWrapper) > 590000) {
            //进行人员库的创建
            maxGroupId++;
            if (userFaceUtils.CreateGroup(maxGroupId.toString(), "重逢" + maxGroupId)) {
                //人员库创建成功,数据库添加
                FaceGroupInfo faceGroupInfo = new FaceGroupInfo(maxGroupId, "重逢" + maxGroupId);
                try {
                    faceGroupInfoDAO.insert(faceGroupInfo);
                } catch (Exception e) {
                }
            }
        }
        return maxGroupId.toString();
    }

    //未创建人员时上传人脸
    public ResponseBean insertUserFace(Integer uId, MultipartFile file) {
        if (uId == null || file.isEmpty() == true) {
            return new ResponseBean(404, "缺少参数", null);
        }
        //准备工作
        String groupId = groupCheck();
        User user = userDAO.selectById(uId);
        user.setuRealName("noUserName");
        String fileBase64 = null;
        try {
            //file转base64
            fileBase64 = MyMiniUtils.multipartFileToBase64(file);
        } catch (Exception e) {
            return new ResponseBean(401, "文件读取异常", null);
        }
        //添加到腾讯服务器
        CreatePersonResponse resp = userFaceUtils.CreatePerson(uId.toString(), user.getuRealName(), groupId, fileBase64);
        if (resp == null) {
            return new ResponseBean(405, "服务器异常，人脸保存失败", null);
        }
        //在数据库中使用户与人员库绑定
        FaceGroupUser faceGroupUser = new FaceGroupUser(groupId, uId);
        try {
            faceGroupUserDAO.insert(faceGroupUser);
        } catch (Exception e) {
            return new ResponseBean(406, "数据库错误", null);
        }
        //将图片上传到自家服务器
        String newName = MyMiniUtils.upLoadFile(file, userFaceDir);
        if (newName == null) {
            return new ResponseBean(405, "服务器异常，人脸保存失败", null);
        }
        //图片与用户对应添加进数据库
        UserFace userFace = new UserFace(resp.getFaceId(), uId, newName);
        if (userFaceDAO.insert(userFace) == 1) {
            userFace = userFaceDAO.selectUserFace(uId, resp.getFaceId());
            userFace.setUrl("https://" + website + userFaceDir + userFace.getUrl());
            return new ResponseBean(200, "人脸保存成功", userFace);
        }
        return new ResponseBean(406, "数据库错误", null);
    }


    //已创建人员增加人脸
    public ResponseBean addUserFace(Integer uId, MultipartFile[] files) {
        if (uId == null || files == null || files.length == 0) {
            return new ResponseBean(404, "缺少参数", null);
        }
        List filesBase64List = new ArrayList();
        for (MultipartFile file : files) {
            try {
                filesBase64List.add(MyMiniUtils.multipartFileToBase64(file));
            } catch (Exception e) {
                return new ResponseBean(406, "文件读取异常", null);
            }
        }
        //上传到腾讯服务器
        CreateFaceResponse resp = userFaceUtils.CreateFace(uId.toString(), filesBase64List);
        if (resp.getSucFaceNum() <= 0) {
            System.out.println(CreatePersonRequest.toJsonString(resp));
            return new ResponseBean(405, "所有图片不符合要求,上传失败", null);
        }
        String[] faceIds = resp.getSucFaceIds(); //成功添加的人脸id
        System.out.println("faceId:" + JSON.toJSONString(faceIds));
        Long[] sucIndexes = resp.getSucIndexes(); //成功添加人脸id的下标
        System.out.println("sucIndexes" + JSON.toJSONString(sucIndexes));
        String newName = null;
        UserFace userFace = new UserFace();
        List userFaceList = new ArrayList();
        for (int i = 0; i < sucIndexes.length; i++) {
            newName = MyMiniUtils.upLoadFile(files[sucIndexes[i].intValue()], userFaceDir); //将成功上传的人脸保存到数据库
            userFace.setFaceId(faceIds[i]);
            userFace.setUid(uId);
            userFace.setUrl(newName);
            userFaceDAO.insert(userFace);
            userFace.setUrl("https://" + website + userFaceDir + newName);
            userFaceList.add(userFace);
        }
        if (sucIndexes.length < files.length) { //如果存在失败的人脸
            List list = new ArrayList();
            list.add(sucIndexes); //成功的图片下标
            return new ResponseBean(201, "有部分图片未上传成功，以下是成功上传的人脸id(后面的数组是成功图片的下标)", list);
        } else {
            return new ResponseBean(200, "全部图片上传成功", userFaceList.get(0));
        }
    }

    //删除人脸id集合
    public ResponseBean deleteFace(Integer uId, String faceIdJson) {
        List<String> facesIdList = JSON.parseArray(faceIdJson, String.class);
        //腾讯云中删除
        DeleteFaceResponse resp = userFaceUtils.DeleteFace(uId.toString(), facesIdList);
        //如果有部分失败，则把失败删除的id填加进来
        List failFaceId = new ArrayList();
        if (resp.getSucDeletedNum() != facesIdList.size()) {
            failFaceId.addAll(facesIdList);
            for (String sucFaceId : resp.getSucFaceIds()) {
                failFaceId.remove(sucFaceId);
            }
        }
        //自己数据库中删除
        List fail = new ArrayList();
        for (String faceId : facesIdList) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("face_id", faceId);
            if (userFaceDAO.delete(queryWrapper) != 1) {
                fail.add(faceId);
            }
        }
        if (fail.isEmpty() == true && failFaceId.size() == 0) {
            return new ResponseBean(200, "人脸图片删除成功", null);
        } else if (fail.isEmpty() == false && failFaceId.size() == 0) {
            return new ResponseBean(201, "人脸图片删除成功，以下face_id在数据库中未找到图片", fail);
        } else {
            Map map = new HashMap();
            map.put("人脸识别端未找到", failFaceId);
            map.put("数据库端未找到", fail);
            return new ResponseBean(202, "部分人脸图片删除失败,请却人脸id", map);
        }
    }

    //用户id集合来找用户公开的信息
    public ResponseBean selectUserList(String uIdListJson) {
        List<Integer> uIdList = JSON.parseArray(uIdListJson, Integer.class);
        List<User> userList = new ArrayList<>();
        for (Integer uId : uIdList) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.select("u_id", "u_uuid", "u_name", "u_sex", "u_birthday", "u_head_portrait", "u_province", "u_city", "u_district", "u_address", "u_volunteer", "u_signature");
            queryWrapper.eq("u_id", uId);
            User user = userDAO.selectOne(queryWrapper);
            user.setuHeadPortrait("https://" + website + headPhoto + user.getuHeadPortrait());
            userList.add(user);
        }
        return new ResponseBean(200, "用户信息搜索成功", userList);
    }

    //搜索人脸
    public ResponseBean SearchFaces(MultipartFile file) {
        if (file.isEmpty() == true) {
            return new ResponseBean(404, "缺少参数", null);
        }
        String fileBase64 = null;
        try {
            fileBase64 = MyMiniUtils.multipartFileToBase64(file);
        } catch (Exception e) {
            return new ResponseBean(406, "文件读取异常", null);
        }
        SearchFacesResponse resp = null;
        try {
            //做一次判断，目前有几个人员库了,腾讯有限制不能多于十个人员库
            List groups = faceGroupInfoDAO.selectGroupId();

            resp = userFaceUtils.SearchFaces(groups, fileBase64);
        } catch (NumberFormatException e) {
            return new ResponseBean(405, "服务器错误", null);
        }
        List<UserFace> userFaces = new ArrayList<>();
        for (Candidate candidate : resp.getResults()[0].getCandidates()) {
            UserFace userFace = new UserFace();
            userFace.setUid(Integer.valueOf(candidate.getPersonId()));
            userFace.setFaceId(candidate.getFaceId());
            userFace.setProbability(candidate.getScore());
            userFaces.add(userFace);
        }
        //获取这些用户的公开信息
        for (int i = 0; i < userFaces.size(); i++) {
            User user = userDAO.selectById(userFaces.get(i).getUid());
            userFaces.get(i).setHeader("https://" + website + headPhoto + user.getuHeadPortrait());//修改头像
            userFaces.get(i).setNickName(user.getuName());
            userFaces.get(i).setIntroduce(user.getuSignature());
        }
        return new ResponseBean(200, "搜索成功", userFaces);

    }

    //用户id来找所有上传的人脸
    public ResponseBean findUserFaces(Integer uId) {
        System.out.println(uId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uId);
        List<UserFace> userFaces = userFaceDAO.selectList(queryWrapper);
        if (userFaces == null) {
            return new ResponseBean(404, "此用户未上传人脸", null);
        }
        int i;
        for (i = 0; i < userFaces.size(); i++) {
            userFaces.get(i).setUrl("https://" + website + userFaceDir + userFaces.get(i).getUrl());
        }
        return new ResponseBean(200, "查询成功", userFaces);
    }

}
