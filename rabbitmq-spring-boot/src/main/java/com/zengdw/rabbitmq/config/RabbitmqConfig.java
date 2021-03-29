package com.zengdw.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/3/15 10:21
 * @description Rabbitmq配置
 */
@Configuration
public class RabbitmqConfig {
    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate();
        configurer.configure(template, connectionFactory);
        // 消息未投递到queue时回调
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("消息发送失败");
            System.out.println("msg:" + new String(message.getBody(), StandardCharsets.UTF_8));
            System.out.println("properties:" + message.getMessageProperties().toString());
            System.out.println("replyCode:" + replyCode);
            System.out.println("replyText:" + replyText);
            System.out.println("exchange:" + exchange);
            System.out.println("routingKey:" + routingKey);
        });
        // 消息未投递到exchange时回调
        // 当使用rabbitmq_delayed_message_exchange插件时 这个回调不管成功与否都会回调
        template.setConfirmCallback((var1, var2, var3) -> {
            System.out.println(var1 != null ? var1.toString() : "");
            System.out.println(var2);
            System.out.println(var3);
        });
        return template;
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue delayQueue() {
        return new Queue("delayQueue");
    }

    @Bean
    public Queue delayQueue1() {
        return new Queue("delayQueue1");
    }

    /**
     * 死信交换机
     */
    @Bean
    public FanoutExchange delayExchange() {
        return ExchangeBuilder.fanoutExchange("delayExchange").build();
    }

    @Bean
    public DirectExchange delayDirectExchange() {
        return ExchangeBuilder.directExchange("delayDirectExchange").build();
    }

    @Bean
    public Binding dealyBinding(FanoutExchange delayExchange, Queue delayQueue) {
        return BindingBuilder.bind(delayQueue).to(delayExchange);
    }

    @Bean
    public Binding dealyBinding1(DirectExchange delayDirectExchange, Queue delayQueue1) {
        return BindingBuilder.bind(delayQueue1).to(delayDirectExchange).with("direct1");
    }

    @Bean
    public Binding dealyBinding2(DirectExchange delayDirectExchange, Queue delayQueue) {
        return BindingBuilder.bind(delayQueue).to(delayDirectExchange).with("direct");
    }

    @Bean
    public Queue queue1() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "delayDirectExchange");
        // 队列中的消息未被消费 10 秒后过期
        // map.put("x-message-ttl", 10000);
        return new Queue("queue1", true, false, false, map);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("fanoutExchange").build();
    }

    @Bean
    public Binding binding1(Queue queue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue1).to(fanoutExchange);
    }

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange("directExchange").build();
    }

    @Bean
    public Binding binding2(DirectExchange directExchange, Queue queue1) {
        return BindingBuilder.bind(queue1).to(directExchange).with("direct");
    }

    @Bean
    public Binding binding3(DirectExchange directExchange, Queue queue1) {
        return BindingBuilder.bind(queue1).to(directExchange).with("direct1");
    }

    /**
     * 自定义交换器 延迟交换器 rabbitmq_delayed_message_exchange插件
     */
    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        /*
          x-delayed-message: 指定交换器类型 由插件(rabbitmq_delayed_message_exchange)提供
         */
        return new CustomExchange("customExchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue queue2() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "delayDirectExchange");
        map.put("x-dead-letter-routing-key", "direct");
        return new Queue("queue2", true, false, false, map);
    }

    @Bean
    public Binding binding4(CustomExchange customExchange, Queue queue2) {
        return BindingBuilder.bind(queue2).to(customExchange).with("delayExchange").noargs();
    }
}