package org.kartishan.bookviewcountservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String BOOK_VIEW_QUEUE = "bookViewIncrementQueue";
    public static final String BOOK_VIEW_EXCHANGE = "bookViewIncrementExchange";
    public static final String BOOK_VIEW_ROUTING_KEY = "bookViewIncrement.update";

    @Bean
    public Queue bookRatingQueue() {
        return new Queue(BOOK_VIEW_QUEUE, true);
    }

    @Bean
    TopicExchange bookRatingExchange() {
        return new TopicExchange(BOOK_VIEW_EXCHANGE);
    }

    @Bean
    Binding bookRatingBinding(Queue bookRatingQueue, TopicExchange bookRatingExchange) {
        return BindingBuilder.bind(bookRatingQueue).to(bookRatingExchange).with(BOOK_VIEW_ROUTING_KEY);
    }

}