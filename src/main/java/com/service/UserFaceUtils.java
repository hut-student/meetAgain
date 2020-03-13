package com.service;

import com.alibaba.fastjson.JSON;
import com.dao.FaceGroupUserDAO;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20180301.IaiClient;
import com.tencentcloudapi.iai.v20180301.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserFaceUtils {

    @Value("${tencent.secret.id}")
    private String secretId;

    @Value("${tencent.secret.key}")
    private String sercretKey;

    @Value("${tencent.service.region}")
    private String region;

    @Autowired
    private FaceGroupUserDAO faceGroupUserDAO;


    //创建人员库
    public boolean CreateGroup(String groupId, String groupName) {
        try {
            Credential cred = new Credential(secretId, sercretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, region, clientProfile);
            Map inputMap = new HashMap();
            inputMap.put("GroupName", groupName);
            inputMap.put("GroupId", groupId);
            inputMap.put("FaceModelVersion", "3.0");
            String params = JSON.toJSONString(inputMap);
            CreateGroupRequest req = CreateGroupRequest.fromJsonString(params, CreateGroupRequest.class);

            CreateGroupResponse resp = client.CreateGroup(req);

            return true;
        } catch (TencentCloudSDKException e) {
            e.toString();
            return false;
        }
    }


    //创建人员
    public CreatePersonResponse CreatePerson(String uId, String realName, String groupId, String imgBase64) {
        try {
            //密钥和密匙
            Credential cred = new Credential(secretId, sercretKey);
            //调用的接口
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, region, clientProfile);
            Map<String, String> inputMap = new HashMap<>();
            inputMap.put("GroupId", groupId);
            inputMap.put("PersonName", realName);
            inputMap.put("PersonId", uId);
            inputMap.put("Image", imgBase64);
            System.out.println("map:" + JSON.toJSONString(inputMap));
            String params = JSON.toJSONString(inputMap);
            CreatePersonRequest req = CreatePersonRequest.fromJsonString(params, CreatePersonRequest.class);

            CreatePersonResponse resp = client.CreatePerson(req);
            System.out.println(CreatePersonRequest.toJsonString(resp));
            return resp;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    //添加人脸
    public CreateFaceResponse CreateFace(String personId, List<String> filesBase64) {
        try {
            Credential cred = new Credential(secretId, sercretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, region, clientProfile);
            Map map = new HashMap();
            map.put("PersonId", personId);
            map.put("Images", filesBase64);
            map.put("QualityControl", 1);
            String params = JSON.toJSONString(map);
            CreateFaceRequest req = CreateFaceRequest.fromJsonString(params, CreateFaceRequest.class);

            CreateFaceResponse resp = client.CreateFace(req);
            return resp;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
    }


    //删除人脸
    public DeleteFaceResponse DeleteFace(String uId, List<String> faceIdList) {
        try {
            Credential cred = new Credential(secretId, sercretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, region, clientProfile);
            Map map = new HashMap();
            map.put("PersonId", uId);
            map.put("FaceIds", faceIdList);

            String params = JSON.toJSONString(map);
            DeleteFaceRequest req = DeleteFaceRequest.fromJsonString(params, DeleteFaceRequest.class);

            DeleteFaceResponse resp = client.DeleteFace(req);

            return resp;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    //人脸搜索
    public SearchFacesResponse SearchFaces(List groups, String fileBase64) {
        try {

            Credential cred = new Credential(secretId, sercretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            IaiClient client = new IaiClient(cred, region, clientProfile);
            Map map = new HashMap();
            map.put("GroupIds", groups);
            map.put("Image", fileBase64);
            map.put("MaxPersonNum", 5);//返回相似度最高的多少人
            String params = JSON.toJSONString(map);
            SearchFacesRequest req = SearchFacesRequest.fromJsonString(params, SearchFacesRequest.class);

            SearchFacesResponse resp = client.SearchFaces(req);

            return resp;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }

    }

}

