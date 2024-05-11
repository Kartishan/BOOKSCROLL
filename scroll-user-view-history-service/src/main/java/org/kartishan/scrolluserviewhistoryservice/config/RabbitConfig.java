package org.kartishan.scrolluserviewhistoryservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String SCROLL_USER_HISTORY_QUEUE = "scrollUserHistoryQueue";
    public static final String SCROLL_USER_HISTORY_EXCHANGE = "scrollUserHistoryExchange";
    public static final String SCROLL_USER_HISTORY_ROUTING_KEY = "scrollUserHistoryRoutingKey";

    @Bean
    public Queue myQueue() {
        return new Queue(SCROLL_USER_HISTORY_QUEUE, false);
    }

    @Bean
    TopicExchange myExchange() {
        return new TopicExchange(SCROLL_USER_HISTORY_EXCHANGE);
    }

    @Bean
    Binding binding(Queue myQueue, TopicExchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with(SCROLL_USER_HISTORY_ROUTING_KEY);
    }

}