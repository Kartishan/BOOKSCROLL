package org.kartishan.scrolluserviewhistoryservice.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.kartishan.scrolluserviewhistoryservice.config.RabbitConfig;
import org.kartishan.scrolluserviewhistoryservice.model.UserScrollViewHistoryDTO;
import org.kartishan.scrolluserviewhistoryservice.service.UserScrollViewHistoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMQConsumer {
    private final UserScrollViewHistoryService userScrollViewHistoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitConfig.SCROLL_USER_HISTORY_QUEUE)
    public void receiveMessage(String message) {
        try {
            UserScrollViewHistoryDTO userScrollViewHistoryDTO = objectMapper.readValue(message, UserScrollViewHistoryDTO.class);
            userScrollViewHistoryService.saveUserScrollViewHistory(userScrollViewHistoryDTO.getUserId(), userScrollViewHistoryDTO.getScrollId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
