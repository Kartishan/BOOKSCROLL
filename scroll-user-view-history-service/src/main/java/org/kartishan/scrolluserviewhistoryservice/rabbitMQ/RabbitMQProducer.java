package org.kartishan.scrolluserviewhistoryservice.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue(String queueName, String message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
