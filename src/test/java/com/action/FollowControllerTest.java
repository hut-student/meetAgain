package com.action;

import com.boot.MainBoot;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainBoot.class)
class FollowControllerTest {

    @Autowired
    private FollowController followController;

    @Autowired
    private FanController fanController;

    @Test
    public void test(){
        System.out.println(fanController.followThisUserOfAllUserPublicInfo(3));
    }
}