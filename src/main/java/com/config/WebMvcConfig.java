package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${upload.headPortrait.dir}")
    private String headPhoto;

    @Value("${upload.findPeople.dir}")
    private String findPeople;

    @Value("${upload.feedBack.dir}")
    private String feedBack;

    @Value("${upload.userFace.dir}")
    private String userFace;

    @Value("${upload.meetAgainSpace.dir}")
    private String meetAgainSpace;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/www/headPhoto/**").addResourceLocations("file://" + headPhoto);
        registry.addResourceHandler("/www/findPeople/**").addResourceLocations("file://" + findPeople);
        registry.addResourceHandler("/www/feedBack/**").addResourceLocations("file://" + feedBack);
        registry.addResourceHandler("/www/userFace/**").addResourceLocations("file://" + userFace);
        registry.addResourceHandler("/www/meetAgainSpace/**").addResourceLocations("file://" + meetAgainSpace);
    }
}
