package com.cloud.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class ExampleRedissonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleRedissonApplication.class, args);
    }


}
