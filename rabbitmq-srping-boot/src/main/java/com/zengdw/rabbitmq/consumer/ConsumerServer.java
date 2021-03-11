package com.zengdw.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/3/11 9:42
 * @description 消息消费者
 */
@Component
public class ConsumerServer {
    @RabbitListener(queues = "easy_queue")
    public void process1(String msg){
        System.out.println("消费者1："+msg);
    }

    @RabbitListener(queues = "easy_queue")
    public void process2(String msg){
        System.out.println("消费者2："+msg);
    }
}
