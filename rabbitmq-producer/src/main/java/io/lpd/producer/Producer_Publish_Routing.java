package io.lpd.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_Publish_Routing {

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
        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, false, null);
        String messageInfo = "INFO";
        String messageError = "ERROR";
        channel.basicPublish(EXCHANGE_NAME, Severity.INFO.getType(), null, messageInfo.getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, Severity.ERROR.getType(), null, messageError.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + messageInfo + "'");
        System.out.println(" [x] Sent '" + messageError + "'");

    }
}
