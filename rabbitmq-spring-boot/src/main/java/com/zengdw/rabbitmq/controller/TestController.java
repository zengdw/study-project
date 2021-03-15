package com.zengdw.rabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author zengd
 * @date Created in 2021/3/15 10:27
 * @description
 */
@RestController
public class TestController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test2")
    public void test2() {
        rabbitTemplate.convertAndSend("customExchange", "delayExchange1", "Hello World1", message -> {
            message.getMessageProperties().setHeader("x-delay", 5000);
            return message;
        });
    }

    @GetMapping("/test1")
    public void test1() {
        rabbitTemplate.convertAndSend("directExchange", "direct1", "Hello World");
    }

    @GetMapping("/test")
    public void test() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm:ss");
        rabbitTemplate.convertAndSend("directExchange", "direct", now.format(pattern)+":Hello World", message -> {
            message.getMessageProperties().setExpiration("5000");
            return message;
        });
        now = LocalTime.now();
        rabbitTemplate.convertAndSend("directExchange", "direct", now.format(pattern)+":Hello World11", message -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
    }
}
