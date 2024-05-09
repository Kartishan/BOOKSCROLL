package org.kartishan.viewhistory.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.kartishan.viewhistory.model.dto.ViewHistoryMessage;
import org.kartishan.viewhistory.service.UserBookViewHistoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMQConsumer {
    private final UserBookViewHistoryService userBookViewHistoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(String message) {
        try {
            ViewHistoryMessage viewHistoryMessage = objectMapper.readValue(message, ViewHistoryMessage.class);
            userBookViewHistoryService.saveUserBookViewHistory(viewHistoryMessage.getUserId(), viewHistoryMessage.getBookId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
