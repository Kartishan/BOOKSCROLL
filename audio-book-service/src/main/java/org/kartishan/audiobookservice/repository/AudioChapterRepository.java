package org.kartishan.audiobookservice.repository;

import org.kartishan.audiobookservice.model.AudioBook;
import org.kartishan.audiobookservice.model.AudioChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AudioChapterRepository extends JpaRepository<AudioChapter, UUID> {
    Optional<AudioChapter> findByAudioBookAndNumber(AudioBook audioBook, Long number);
}
