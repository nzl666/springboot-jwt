package com.liumapp.helloSv.backend.web;

import com.liumapp.helloSv.backend.web.utils.MyMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by liumapp on 10/9/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@MapperScan(basePackages = "com.liumapp" , markerInterface = MyMapper.class)
@SpringBootApplication(scanBasePackages = {"com.liumapp"})
@Import(com.huluwa.cc.JWTSend.class)
public class Main extends WebMvcConfigurerAdapter{

    public static void main(String[] args) {
        SpringApplication.run(Main.class , args);
    }

}
