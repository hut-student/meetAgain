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
        System.out.println(userController.verificationLogin("3",1581826297218L,"480F1BEEE2154F5924DC8067C301BA6B"));
    }
}