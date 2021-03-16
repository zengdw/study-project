package com.zengdw.rabbitmq.controller;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

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
        rabbitTemplate.convertAndSend("customExchange", "delayExchange", "Hello World1", message -> {
            // delayed_message_exchange设置延迟时间
            // message.getMessageProperties().setHeader("x-delay", 5000);
            message.getMessageProperties().setDelay(5000);
            // 设置消息持久化到磁盘
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString());
            return message;
        });
    }

    @GetMapping("/test1")
    public void test1() {
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("directExchange1", "direct1", "Hello World", message -> {
            message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString());
            return message;
        }, data);
    }

    @GetMapping("/test")
    public void test() {
        rabbitTemplate.convertAndSend("directExchange", "direct", "Hello World", message -> {
            // 设置消息到期时间
            message.getMessageProperties().setExpiration("5000");
            return message;
        });
    }
}
