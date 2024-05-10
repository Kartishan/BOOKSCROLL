package org.kartishan.scrollviewhistoryservice.service;

import lombok.AllArgsConstructor;
import org.kartishan.scrollviewhistoryservice.model.ScrollView;
import org.kartishan.scrollviewhistoryservice.repository.ScrollViewRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ScrollViewCountService {
    private final ScrollViewRepository scrollViewCountRepository;

    public void incrementViewCount(UUID scrollId){
        ScrollView scrollView = scrollViewCountRepository.findByScrollId(scrollId)
                .orElseGet(() -> {
                    ScrollView newScrollView = new ScrollView();
                    newScrollView.setScrollId(scrollId);
                    newScrollView.setViewCount(0L);
                    return newScrollView;
                });

        scrollView.setViewCount(scrollView.getViewCount() + 1);
        scrollViewCountRepository.save(scrollView);
    }
}
