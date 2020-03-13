package com.service;

import com.boot.MainBoot;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainBoot.class)
class UserFaceServiceTest {

    @Autowired
    private UserFaceService userFaceService;

    @Test
    void selectUserList() {
    }
}