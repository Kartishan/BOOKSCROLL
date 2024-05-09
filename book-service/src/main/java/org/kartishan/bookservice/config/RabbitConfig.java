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

    public static final String BOOK_RATING_QUEUE = "bookRatingQueue";
    public static final String BOOK_RATING_EXCHANGE = "bookRatingExchange";
    public static final String BOOK_RATING_ROUTING_KEY = "book.rating.update";

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