package org.kartishan.audiobookservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.audiobookservice.model.LastPlaybackPosition;
import org.kartishan.audiobookservice.model.LastPlaybackPositionDTO;
import org.kartishan.audiobookservice.request.PlaybackPositionUpdateRequest;
import org.kartishan.audiobookservice.service.LastPlaybackPositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/audio/playback")
@RequiredArgsConstructor
public class LastPlaybackPositionController {
    private final LastPlaybackPositionService lastPlaybackPositionService;

    @PostMapping
    public ResponseEntity<?> updatePlaybackPosition(@RequestBody PlaybackPositionUpdateRequest playbackPositionUpdateRequest, HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }
        LastPlaybackPosition playbackPosition = lastPlaybackPositionService.updatePlaybackPosition(
                userId,
                playbackPositionUpdateRequest.getBookId(),
                playbackPositionUpdateRequest.getChapterNumber(),
                playbackPositionUpdateRequest.getLastPlaybackPosition()
        );

        if (playbackPosition != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Не удалось обновить позицию воспроизведения");
        }
    }

    @GetMapping("/{audioBook}")
    public ResponseEntity<LastPlaybackPositionDTO> getPlaybackPosition(@PathVariable UUID audioBook, HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }
        LastPlaybackPosition playbackPosition = lastPlaybackPositionService.getPlaybackPosition(userId, audioBook);

        LastPlaybackPositionDTO responseDTO = LastPlaybackPositionDTO.builder()
                .chapterId(playbackPosition.getChapter())
                .lastPlaybackPosition(playbackPosition.getLastPlaybackPosition())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/last")
    public ResponseEntity<LastPlaybackPosition> getLastPlaybackPosition(HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }

        LastPlaybackPosition playbackPosition = lastPlaybackPositionService.getLastPlaybackPositionForUser(userId);

        return ResponseEntity.ok(playbackPosition);
    }
}
