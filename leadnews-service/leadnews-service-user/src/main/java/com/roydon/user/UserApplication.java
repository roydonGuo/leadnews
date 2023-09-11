package com.roydon.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * UserApplication
 *
 * @AUTHOR: roydon
 * @DATE: 2023/9/11
 **/
@SpringBootApplication
@EnableDiscoveryClient // nacos 服务注册发现
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
