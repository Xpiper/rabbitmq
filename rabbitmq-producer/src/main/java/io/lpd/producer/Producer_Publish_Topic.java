package io.lpd.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_Publish_Topic {

    private static final String EXCHANGE_NAME = "topic_logs";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.23.128");
        factory.setPort(5672);
        factory.setVirtualHost("/lpd");
        factory.setUsername("lpd");
        factory.setPassword("lpd");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true, false, false, null);
        String messageOne = "quick.orange.rabbit";
        String messageTwo = "quick.orange.fox";
        String messageThree = "lazy.brown.fox";
        channel.basicPublish(EXCHANGE_NAME, messageOne, null, messageOne.getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, messageTwo, null, messageTwo.getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, messageThree, null, messageThree.getBytes("UTF-8"));

        System.out.println(" [x] Sent '" + messageOne + "'");
        System.out.println(" [x] Sent '" + messageTwo + "'");
        System.out.println(" [x] Sent '" + messageThree + "'");


    }
}
