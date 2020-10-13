package com.sengou.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SengouRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SengouRegistryApplication.class, args);
    }
}
