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
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    void addSearch() {
        System.out.println(searchService.addSearch("{\"lostTime\":1581933311511,\"sId\":0,\"sLostTime\":\"2020-02-17T17:55:11.511\",\"sTitle\":\"测试一下\",\"sType\":1,\"uId\":1}"));
    }
}