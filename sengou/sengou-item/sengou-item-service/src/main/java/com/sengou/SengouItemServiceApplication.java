package com.sengou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.sengou.item.mapper")//扫描mapper接口
public class SengouItemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SengouItemServiceApplication.class, args);
    }
}