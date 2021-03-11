package com.zengdw.rabbitmq.provider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/3/10 17:35
 * @description 消息生产者
 */
@RestController
public class ProviderServer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public void send() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertSendAndReceive("fanoutExchange", "a"+i);
        }
    }
}
