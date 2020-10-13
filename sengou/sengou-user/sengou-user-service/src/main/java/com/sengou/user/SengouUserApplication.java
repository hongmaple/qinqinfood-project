package com.sengou.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.sengou.user.mapper")
public class SengouUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SengouUserApplication.class, args);
    }
}