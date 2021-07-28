package com.zengdw.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * @description:
 * @author: zengd
 * @date: 2021/07/28 11:26
 */
@Configuration
@EnableScheduling
public class KafkaConfig {
    @Autowired
    private ConsumerFactory<String, Object> consumerFactory;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    /**
     * '@KafkaListener注解所标注的方法并不会在IOC容器中被注册为Bean，'
     * 而是会被注册在KafkaListenerEndpointRegistry中，
     * 而KafkaListenerEndpointRegistry在SpringIOC中已经被注册为Bean
     **/
    @Resource
    private KafkaListenerEndpointRegistry registry;

    /**
     * 创建一个名为testTopic的Topic并设置分区数为8，分区副本数为2
     * <p>
     * 如果要修改分区数，只需修改配置值重启项目即可
     * 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
     */
    @Bean
    public NewTopic initTopic() {
        return new NewTopic("testTopic", 8, (short) 2);
    }


    /**
     * 异常处理类，可以处理consumer在消费时发生的异常
     * '@KafkaListener(topics = {"testTopic"}, errorHandler = "consumerAwareErrorHandler")'
     */
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            System.out.println("消费异常：" + message.getPayload());
            return null;
        };
    }

    /**
     * 监听器容器工厂
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略 返回true则消息被过滤掉，返回false时，消息能正常抵达监听容器。
        factory.setRecordFilterStrategy(consumerRecord -> Integer.parseInt(consumerRecord.value().toString()) % 2 != 0);
        // 设置回复模板
        factory.setReplyTemplate(kafkaTemplate);
        // 禁止KafkaListener自动启动
        // factory.setAutoStartup(false);
        return factory;
    }

    // 定时启动监听器
    @Scheduled(cron = "0 42 11 * * ? ")
    public void startListener() {
        System.out.println("启动监听器...");
        // @KafkaListener注解后面设置的监听器ID,标识这个监听器
        String listenerId = "topic2-listener";
        if (!registry.getListenerContainer(listenerId).isRunning()) {
            registry.getListenerContainer(listenerId).start();
        }
        //registry.getListenerContainer("timingConsumer").resume();
    }

    // 定时停止监听器
    @Scheduled(cron = "0 45 11 * * ? ")
    public void shutDownListener() {
        System.out.println("关闭监听器...");
        registry.getListenerContainer("topic2-listener").pause();
    }

}
