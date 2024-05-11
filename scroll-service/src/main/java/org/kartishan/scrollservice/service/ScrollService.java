package org.kartishan.scrollservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kartishan.scrollservice.config.RabbitConfig;
import org.kartishan.scrollservice.feign.AuthServiceClient;
import org.kartishan.scrollservice.feign.BookServiceClient;
import org.kartishan.scrollservice.model.Scroll;
import org.kartishan.scrollservice.model.dto.BookDTO;
import org.kartishan.scrollservice.model.dto.ScrollDTO;
import org.kartishan.scrollservice.model.dto.UserDTO;
import org.kartishan.scrollservice.model.dto.UserScrollViewHistoryDTO;
import org.kartishan.scrollservice.rabbitMQ.RabbitMQProducer;
import org.kartishan.scrollservice.repository.ScrollRepository;
import org.kartishan.scrollservice.request.ScrollRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrollService {
    private final ScrollRepository scrollRepository;
    private final AuthServiceClient authServiceClient;
    private final BookServiceClient bookServiceClient;
    private final RabbitMQProducer rabbitMQProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Scroll getScroll(UUID id){
        Scroll scroll = scrollRepository.findById(id).orElseThrow();
        return scroll;
    }
    public void createScroll(UUID userId, ScrollRequest scrollRequest) {
        try {
            Scroll scroll = new Scroll();
            scroll.setName(scrollRequest.getName());
            scroll.setBookId(scrollRequest.getBookId());
            scroll.setUserId(userId);
            scroll.setCfiRange(scrollRequest.getCfiRange());
            scrollRepository.save(scroll);
        }catch (Exception e){
            log.error("Error while creating scroll", e);
            throw e;
        }
    }

    public ScrollDTO getScrollDetails(UUID id, UUID userId) {
        Scroll scroll = scrollRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scroll not found"));

        UserDTO user = authServiceClient.getUserById(scroll.getUserId());
        BookDTO book = bookServiceClient.getBookById(scroll.getBookId());

        if (userId != null){
            processScrollView(userId, book.getId());
        }

        ScrollDTO scrollDTO = ScrollDTO.builder()
                .id(scroll.getId())
                .name(scroll.getName())
                .bookId(scroll.getBookId())
                .bookName(book.getName())
                .userId(scroll.getUserId())
                .username(user.getUsername())
                .build();
        return scrollDTO;
    }

    public void processScrollView(UUID scrollId, UUID userId) {
        UserScrollViewHistoryDTO userScrollViewHistoryDTO = new UserScrollViewHistoryDTO(scrollId, userId, new Date());
        try{
            String viewAsString = objectMapper.writeValueAsString(userScrollViewHistoryDTO);
            rabbitMQProducer.sendToQueue(RabbitConfig.SCROLL_USER_HISTORY_QUEUE,viewAsString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
