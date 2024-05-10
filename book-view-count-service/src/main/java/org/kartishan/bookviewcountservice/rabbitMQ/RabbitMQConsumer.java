package org.kartishan.bookviewcountservice.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import org.kartishan.bookviewcountservice.config.RabbitConfig;
import org.kartishan.bookviewcountservice.service.BookViewCountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RabbitMQConsumer {
    private BookViewCountService bookViewCountService;

    @RabbitListener(queues = RabbitConfig.BOOK_VIEW_QUEUE)
    public void receiveBookViewIncrementMessage(String bookIdAsString) {
        UUID bookId = UUID.fromString(bookIdAsString);
        bookViewCountService.incrementViewCount(bookId);
    }
}
