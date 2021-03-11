package com.zengdw.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/3/10 17:31
 * @description rabbit配置
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue1(){
        return new Queue("easy_queue_one");
    }

    @Bean
    public Queue queue2(){
        return new Queue("easy_queue_two");
    }

    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding binding1(Queue queue1, FanoutExchange exchange){
        return BindingBuilder.bind(queue1).to(exchange);
    }

    @Bean
    public Binding binding2(Queue queue2, FanoutExchange exchange){
        return BindingBuilder.bind(queue2).to(exchange);
    }
}
