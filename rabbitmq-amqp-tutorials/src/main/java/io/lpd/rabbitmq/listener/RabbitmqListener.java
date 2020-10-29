package io.lpd.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitmqListener {


    @RabbitListener(queues = "boot_queue")
    public void ListenerQueue(Message message) {
        System.out.println(new String(message.getBody()));
    }


}
