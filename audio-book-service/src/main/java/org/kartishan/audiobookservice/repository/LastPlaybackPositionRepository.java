package org.kartishan.audiobookservice.repository;

import org.kartishan.audiobookservice.model.LastPlaybackPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LastPlaybackPositionRepository extends JpaRepository<LastPlaybackPosition, UUID> {

    Optional<LastPlaybackPosition> findByUserIdAndAudioBookId(UUID userId, UUID audioBookId);

    Optional<LastPlaybackPosition> findFirstByUserIdOrderByUpdatedAtDesc(UUID userId);
}
