package com.action;

import com.boot.MainBoot;
import com.utils.MyMiniUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainBoot.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void verificationLogin() {
        System.out.println(userController.updateUser("1",1582789545112L, "A199ABAED0B2375F58CF6EA0F60B50C8","{\"uId\":1,\"uSex\":1,\"uUpdateTime\":\"2020-02-27T15:45:45.203\"}"));
    }
}