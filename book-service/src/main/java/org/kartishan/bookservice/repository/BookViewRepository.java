package org.kartishan.bookservice.repository;

import org.kartishan.bookservice.model.BookView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookViewRepository extends JpaRepository<BookView, UUID> {
    Optional<BookView> findByBookId(UUID bookId);

    Page<BookView> findAllByOrderByViewCountDesc(Pageable pageable);
}
