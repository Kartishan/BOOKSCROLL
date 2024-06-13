package org.kartishan.audiobookservice.repository;

import org.kartishan.audiobookservice.model.AudioBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AudioBookRepository extends JpaRepository<AudioBook, UUID> {
    Optional<AudioBook> findByBookId(UUID bookId);
}
