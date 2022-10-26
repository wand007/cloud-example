package com.cloud.example.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/4
 */
@EnableAsync
@SpringBootApplication
public class ExampleToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleToolsApplication.class, args);
    }

}
