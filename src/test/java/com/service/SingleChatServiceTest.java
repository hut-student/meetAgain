package com.service;

import com.alibaba.fastjson.JSON;
import com.boot.MainBoot;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainBoot.class)
class SingleChatServiceTest {

    @Autowired
    private SingleChatService singleChatService;

    @Test
    void insertOneChat() {
       System.out.println(singleChatService.findUserSingleChat(1));
    }
}