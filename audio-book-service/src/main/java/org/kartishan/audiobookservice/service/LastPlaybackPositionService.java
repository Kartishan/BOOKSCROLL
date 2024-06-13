package org.kartishan.audiobookservice.service;

import lombok.RequiredArgsConstructor;
import org.kartishan.audiobookservice.model.LastPlaybackPosition;
import org.kartishan.audiobookservice.repository.LastPlaybackPositionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LastPlaybackPositionService {
    private final LastPlaybackPositionRepository lastPlaybackPositionRepository;

    public LastPlaybackPosition updatePlaybackPosition(UUID userId, UUID audioBookId, Long chapterId, double position) {
        LastPlaybackPosition playbackPosition = lastPlaybackPositionRepository
                .findByUserIdAndAudioBookId(userId, audioBookId)
                .orElse(new LastPlaybackPosition());

        playbackPosition.setUserId(userId);
        playbackPosition.setAudioBookId(audioBookId);
        playbackPosition.setChapter(chapterId);
        playbackPosition.setLastPlaybackPosition(position);

        return lastPlaybackPositionRepository.save(playbackPosition);
    }

    public LastPlaybackPosition getPlaybackPosition(UUID userId, UUID audioBookId) {
        return lastPlaybackPositionRepository.findByUserIdAndAudioBookId(userId, audioBookId)
                .orElseThrow(() -> new RuntimeException("Playback position not found"));
    }

    public LastPlaybackPosition getLastPlaybackPositionForUser(UUID userId) {
        return lastPlaybackPositionRepository.findFirstByUserIdOrderByUpdatedAtDesc(userId)
                .orElseThrow(() -> new RuntimeException("Playback position not found"));
    }

}
