package org.kartishan.ratingservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.ratingservice.model.UserBookPreference;
import org.kartishan.ratingservice.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/update")
    public ResponseEntity<Void> updateRating(@RequestParam UUID bookId, @RequestParam int rating, HttpServletRequest request) {
        System.out.println(bookId);
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }
        ratingService.updateUserBookPreference(userId, bookId, rating);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserBookPreference>> getUserPreferences(@PathVariable UUID userId) {
        List<UserBookPreference> preferences = ratingService.findPreferencesByUser(userId);
        return ResponseEntity.ok(preferences);
    }
    @GetMapping("/preferences")
    public ResponseEntity<List<UserBookPreference>> getAllPreferences() {
        List<UserBookPreference> preferences = ratingService.findAllPreferences();
        return ResponseEntity.ok(preferences);
    }
}
