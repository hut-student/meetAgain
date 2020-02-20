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
class FeedBackServiceTest {

    @Autowired
    private FeedBackService feedBackService;

    @Test
    void insertFeedBack() {
        System.out.println(feedBackService.insertFeedBack(1,1582185290643L, "129E84EA13593B02E301442D69D1E0E7", "{\"fContent\":\"快点搞\",\"uId\":1}"));
    }
}