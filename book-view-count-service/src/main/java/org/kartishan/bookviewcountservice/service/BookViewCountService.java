package org.kartishan.bookviewcountservice.service;

import lombok.AllArgsConstructor;
import org.kartishan.bookviewcountservice.model.BookViewCount;
import org.kartishan.bookviewcountservice.repositroty.BookViewCountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BookViewCountService {
    private final BookViewCountRepository bookViewCountRepository;

    public void incrementViewCount(UUID bookId){
        BookViewCount bookViewCount = bookViewCountRepository.findById(bookId)
                .orElseGet(() -> new BookViewCount(bookId, 0L));
        bookViewCount.setViewCount(bookViewCount.getViewCount() + 1);
        bookViewCountRepository.save(bookViewCount);
    }

    public Long getViewCount(UUID bookId) {
        return bookViewCountRepository.findById(bookId)
                .map(BookViewCount::getViewCount)
                .orElse(0L);
    }
}
