package org.kartishan.bookviewcountservice.service;

import lombok.AllArgsConstructor;
import org.kartishan.bookviewcountservice.feign.BookServiceClient;
import org.kartishan.bookviewcountservice.model.BookViewCount;
import org.kartishan.bookviewcountservice.model.dto.BookDTO;
import org.kartishan.bookviewcountservice.repositroty.BookViewCountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookViewCountService {
    private final BookViewCountRepository bookViewCountRepository;
    private final BookServiceClient bookServiceClient;

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

    public Page<BookDTO> getTopViewedBooks(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<BookViewCount> bookViewCountsPage = bookViewCountRepository.findTopViewedBooks(pageable);

        List<UUID> bookIds = bookViewCountsPage.getContent().stream()
                .map(BookViewCount::getBookId)
                .collect(Collectors.toList());

        List<BookDTO> bookDTOs = bookServiceClient.getBooksByIds(bookIds);

        return new PageImpl<>(bookDTOs, pageable, bookViewCountsPage.getTotalElements());
    }
}
