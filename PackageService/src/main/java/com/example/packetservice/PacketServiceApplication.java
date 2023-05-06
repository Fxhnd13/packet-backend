package com.example.packetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.example.authconfigurations","com.example.packetservice"})
@EnableDiscoveryClient
public class PacketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PacketServiceApplication.class, args);
    }

}
