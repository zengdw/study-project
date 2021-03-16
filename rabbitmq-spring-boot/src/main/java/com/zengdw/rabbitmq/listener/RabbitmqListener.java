package com.zengdw.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/3/15 10:42
 * @description rabbitmq消息监听器
 */
@Component
public class RabbitmqListener {
    /**
     * channel.basicRecover(true|false): 消息返回队列，true：表示消息可能会投递给其他消费者，false：表示消息重新投递给自己
     * channel.basicNack(tag, false, true): 表示拒绝tag标识的消息
     *      第二个参数：false表示只拒绝当前消息 true表示会拒绝之前未ack的所有消息
     *      第三个参数：false表示拒绝的消息不会回到队列 true表示拒绝的消息重新加入队列
     * channel.basicReject(tag, true): 表示拒绝tag标识的消息
     *      第二个参数：false表示拒绝的消息不会回到队列 true表示拒绝的消息重新加入队列
     *      与basicNack的区别就是basicNack可以一次nack多条消息
     */

    @RabbitListener(queues = "queue2")
    public void processQueue(String msg, Channel channel, Message message) throws IOException {
        System.out.println(msg);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }

    @RabbitListener(queues = "delayQueue")
    public void process(String msg, Channel channel, Message message) throws IOException {
        System.out.println("delayQueue:" + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "delayQueue1")
    public void process1(String msg, Channel channel, Message message) throws IOException {
        System.out.println("delayQueue1:" + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "queue1")
    public void process2(String msg, Channel channel, Message message) throws IOException {
        System.out.println("queue1:" + msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicRecover(false);
        }
    }
}
