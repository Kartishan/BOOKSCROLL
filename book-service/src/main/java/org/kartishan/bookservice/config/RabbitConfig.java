package org.kartishan.bookservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String MY_QUEUE = "myQueue";
    public static final String MY_EXCHANGE = "myExchange";
    public static final String MY_ROUTING_KEY = "my.routing.key";

    @Bean
    public Queue myQueue() {
        return new Queue(MY_QUEUE, false);
    }

    @Bean
    TopicExchange myExchange() {
        return new TopicExchange(MY_EXCHANGE);
    }

    @Bean
    Binding binding(Queue myQueue, TopicExchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with(MY_ROUTING_KEY);
    }

}