package org.kartishan.bookchapterservice.service;

import lombok.RequiredArgsConstructor;
import org.kartishan.bookchapterservice.model.BookChapter;
import org.kartishan.bookchapterservice.model.BookChapterDTO;
import org.kartishan.bookchapterservice.repository.BookChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookChapterService {
    private final BookChapterRepository bookChapterRepository;

    public List<BookChapterDTO> getChaptersByBookId(UUID bookId) {
        List<BookChapter> chapters = bookChapterRepository.findByBookIdOrderByNumberAsc(bookId);
        return chapters.stream()
                .map(chapter -> BookChapterDTO.builder()
                        .number(chapter.getNumber())
                        .summary(chapter.getSummary())
                        .build())
                .collect(Collectors.toList());
    }

    public String getChapterSummary(UUID bookId, Long chapterNumber) {
        return bookChapterRepository.findByBookIdAndNumber(bookId, chapterNumber)
                .map(BookChapter::getSummary)
                .orElse(null);
    }
}
