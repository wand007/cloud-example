package com.cloud.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExampleOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleOrderApplication.class, args);
    }


}
