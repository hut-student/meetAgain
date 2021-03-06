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
class SearchCommentServiceTest {

    @Autowired
    private SearchCommentService searchCommentService;

    @Test
    void addSearchComment() {
        System.out.println(searchCommentService.findReply(2,1,2));
    }

}
