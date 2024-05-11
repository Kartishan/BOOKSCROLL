package org.kartishan.bookmarkservice.repository;

import org.kartishan.bookmarkservice.model.UserBookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserBookMarkRepository extends JpaRepository<UserBookMark, UUID> {
    Optional<UserBookMark> findByUserIdAndBookId(UUID userId, UUID bookId);
}
