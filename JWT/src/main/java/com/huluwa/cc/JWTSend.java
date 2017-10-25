package com.huluwa.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/10/24.
 */
@Configuration
@SpringBootApplication(scanBasePackages = {"com.huluwa"})
//@ServletComponentScan(basePackages = {"com.huluwa"})
public class JWTSend {
        /**
         * 使用代码注册Servlet（不需要@ServletComponentScan注解）
         */
        public static void main(String[] args) {
            SpringApplication.run(JWTSend.class, args);
        }
}
