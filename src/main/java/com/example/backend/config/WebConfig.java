package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // すべてのリクエストを index.html にフォワード
        registry.addViewController("/{spring}").setViewName("forward:/index.html");
        registry.addViewController("/**/{spring}").setViewName("forward:/index.html");
        registry.addViewController("/{spring}/**").setViewName("forward:/index.html");
    }
}