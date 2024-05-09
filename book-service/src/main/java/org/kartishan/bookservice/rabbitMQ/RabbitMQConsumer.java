package org.kartishan.bookservice.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.kartishan.bookservice.config.RabbitConfig;
import org.kartishan.bookservice.model.dto.RatingUpdateMessage;
import org.kartishan.bookservice.service.BookRatingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMQConsumer {
    private final ObjectMapper objectMapper;
    private final BookRatingService bookRatingService;

    @RabbitListener(queues = RabbitConfig.BOOK_RATING_QUEUE)
    public void receiveMessage(String message) {
        try {
            RatingUpdateMessage ratingUpdateMessage = objectMapper.readValue(message, RatingUpdateMessage.class);
            bookRatingService.updateBookRating(ratingUpdateMessage.getBookId(), ratingUpdateMessage.getRating());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
