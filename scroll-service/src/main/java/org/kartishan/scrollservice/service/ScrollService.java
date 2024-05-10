package org.kartishan.scrollservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kartishan.scrollservice.model.Scroll;
import org.kartishan.scrollservice.repository.ScrollRepository;
import org.kartishan.scrollservice.request.ScrollRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrollService {
    private final ScrollRepository scrollRepository;

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
}
