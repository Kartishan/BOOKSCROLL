package org.kartishan.bookchapterservice.repository;

import org.kartishan.bookchapterservice.model.BookChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookChapterRepository extends JpaRepository<BookChapter, UUID> {

    Optional<BookChapter> findByBookIdAndNumber(UUID bookId, Long chapterNumber);

    List<BookChapter> findByBookIdOrderByNumberAsc(UUID bookId);
}

