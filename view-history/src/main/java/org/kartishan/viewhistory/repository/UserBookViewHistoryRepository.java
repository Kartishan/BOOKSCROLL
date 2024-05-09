package org.kartishan.viewhistory.repository;

import org.kartishan.viewhistory.model.UserBookViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserBookViewHistoryRepository extends JpaRepository<UserBookViewHistory, Long> {

    Optional<UserBookViewHistory> findByUserIdAndBookId(UUID userId, UUID bookId);

    List<UserBookViewHistory> findByUserId(UUID userId);
}
