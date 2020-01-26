package com.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@ComponentScan({"com.dao","com.service","com.action"})
@MapperScan("com.dao")
public class MainBoot {
    public static void main(String[] args) {
        SpringApplication.run(MainBoot.class, args);
    }
}
