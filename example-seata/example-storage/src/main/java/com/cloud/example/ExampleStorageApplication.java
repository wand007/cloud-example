package com.cloud.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExampleStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleStorageApplication.class, args);
    }


}
