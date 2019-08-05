package com.jpa.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExampleControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleControllerApplication.class, args);
    }


}
