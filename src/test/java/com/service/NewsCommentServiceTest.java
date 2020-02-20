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
class NewsCommentServiceTest {

    @Autowired
    private NewsCommentService newsCommentService;

    @Test
    void addOneCommet() {
        System.out.println(newsCommentService.addOneCommet("震惊两亿人", "2","我猜不信类"));

    }
}