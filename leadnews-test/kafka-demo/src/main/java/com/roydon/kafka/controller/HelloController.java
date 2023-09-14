package com.roydon.kafka.controller;

import com.alibaba.fastjson.JSON;
import com.roydon.kafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/hello")
    public String hello() {
        User user = new User();
        user.setUsername("roydon");
        user.setAge(18);
        kafkaTemplate.send("user-topic", JSON.toJSONString(user));
        return "ok";
    }
}
