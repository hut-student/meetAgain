package com.service;

import com.boot.MainBoot;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainBoot.class)
class NewsReplyServiceTest {

    @Autowired
    private NewsReplyService newsReplyService;

    @Test
    void selectReplyByCommedId() {
        System.out.println(newsReplyService.addOneReply(2,1,1,2,"我来测试下"));
    }
}