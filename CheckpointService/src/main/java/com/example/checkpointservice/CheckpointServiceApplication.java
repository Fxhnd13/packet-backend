package com.example.checkpointservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.example.authconfigurations","com.example.checkpointservice"})
@EnableDiscoveryClient
public class CheckpointServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckpointServiceApplication.class, args);
    }

}
