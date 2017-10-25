package com.huluwa.cc;

import com.huluwa.cc.jwt.ProjectToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/10/24.
 */
@Configuration
@ServletComponentScan(basePackages = {"com.huluwa"})
public class JWTSend {
        /**
         * 使用代码注册Servlet（不需要@ServletComponentScan注解）
         */
        public static void main(String[] args) {
            SpringApplication.run(JWTSend.class, args);
        }
        @Bean
        public ProjectToken getProjectToken() {
            ProjectToken projectToken = new ProjectToken();
            return projectToken;
        }
}
