package io.lpd.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Subscribe1_Routing {

    private static final String EXCHANGE_NAME = "logs";

    public enum Severity {

        INFO("info"), ERROR("error");

        private final String type;

        Severity(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.23.128");
        factory.setPort(5672);
        factory.setVirtualHost("/lpd");
        factory.setUsername("lpd");
        factory.setPassword("lpd");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, Severity.ERROR.getType());
        channel.queueBind(queueName, EXCHANGE_NAME, Severity.INFO.getType());

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

    }


}
