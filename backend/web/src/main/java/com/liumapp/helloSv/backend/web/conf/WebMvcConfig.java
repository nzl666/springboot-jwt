package com.liumapp.helloSv.backend.web.conf;

import com.huluwa.cc.filter.Filter1_CheckToken;
import com.liumapp.helloSv.backend.web.interceptors.GeneralInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(
//                new GeneralInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login/**", "/pull/**", "/user/save", "/user/ifExist/**")
        ;

    }

}
