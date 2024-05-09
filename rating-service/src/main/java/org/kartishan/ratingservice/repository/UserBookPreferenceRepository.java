package org.kartishan.ratingservice.repository;

import org.kartishan.ratingservice.model.UserBookPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserBookPreferenceRepository extends JpaRepository<UserBookPreference, UUID> {

    List<UserBookPreference> findByBookId(UUID bookId);

    Optional<UserBookPreference> findByUserIdAndBookId(UUID userId, UUID bookId);
}
