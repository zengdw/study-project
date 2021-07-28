package com.zengdw.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: zengd
 * @date: 2021/07/28 11:38
 */
@Component
public class KafkaConsumer {
    /**
     * 消费监听
     * spring.kafka.listener.type: single
     * '@SendTo("topic2")'
     *      消息转发,return值即转发的消息内容
     *      且ContainerFactory需要配置ReplyTemplate
     */
    // @SendTo("topic2")
    @KafkaListener(id = "topic2-listener", groupId = "g1", topics = {"testTopic"},
            errorHandler = "consumerAwareErrorHandler", containerFactory = "containerFactory")
    public String onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
        // throw new Exception("模拟异常");
        return "from testTopic:" + record.value();
    }

    /**
     * 同一个分区中的消息 同一个消费组中只能有一个消费者消费
     * kafka只能保证同一分区中的消息有序
     */
    @KafkaListener(topics = {"testTopic"})
    public void onMessage4(ConsumerRecord<?, ?> record) {
        System.out.println("oher-listener:" + record.value());
    }

    @KafkaListener(topics = {"topic2"})
    public void onMessage3(ConsumerRecord<?, ?> record) {
        System.out.println("topic2:" + record.value());
    }

    /**
     * 批量消费
     * spring.kafka.listener.type: batch
     */
    @KafkaListener(topics = {"quickstart"})
    public void onMessage2(List<ConsumerRecord<?, ?>> records) {
        System.out.println(">>>批量消费一次，records.size()=" + records.size());
        for (ConsumerRecord<?, ?> record : records) {
            System.out.println(record.value());
        }
    }
}
