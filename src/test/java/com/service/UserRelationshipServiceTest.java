package com.service;

import com.boot.MainBoot;
import com.dao.SearchDAO;
import com.pojo.Search;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.applet.Main;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainBoot.class)
class UserRelationshipServiceTest {

    @Autowired
    private SearchDAO searchDAO;

    @Autowired
    private UserRelationshipService userRelationshipService;

    @Test
    void checkDistance() {
        System.out.println(userRelationshipService.findDirSearch("200000.39873767446937,200000.923341986717084", 1, 5));
    }
}