package org.kartishan.ratingservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String BOOK_RATING_QUEUE = "bookRatingQueue";
    public static final String BOOK_RATING_EXCHANGE = "bookRatingExchange";
    public static final String BOOK_RATING_ROUTING_KEY = "book.rating.update";

    @Bean
    public Queue bookRatingQueue() {
        return new Queue(BOOK_RATING_QUEUE, true);
    }

    @Bean
    TopicExchange bookRatingExchange() {
        return new TopicExchange(BOOK_RATING_EXCHANGE);
    }

    @Bean
    Binding bookRatingBinding(Queue bookRatingQueue, TopicExchange bookRatingExchange) {
        return BindingBuilder.bind(bookRatingQueue).to(bookRatingExchange).with(BOOK_RATING_ROUTING_KEY);
    }

}