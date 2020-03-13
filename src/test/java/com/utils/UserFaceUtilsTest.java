package com.utils;

import com.boot.MainBoot;
import com.service.UserFaceUtils;
import com.tencentcloudapi.iai.v20180301.models.SearchFacesRequest;
import com.tencentcloudapi.iai.v20180301.models.SearchFacesResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainBoot.class)
class UserFaceUtilsTest {

    @Autowired
    private UserFaceUtils userFaceUtils;

    public static  String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }


    @Test
    void createGroup() {
        List group = new ArrayList();
        group.add(1);
        group.add(2);
        SearchFacesResponse resp = userFaceUtils.SearchFaces(group, UserFaceUtilsTest.fileToBase64("C:\\Users\\DELL\\Desktop\\1.jpg"));
        System.out.println(resp.getFaceNum());
        System.out.println(SearchFacesRequest.toJsonString(resp));
        System.out.println(resp.getRequestId());
    }
}