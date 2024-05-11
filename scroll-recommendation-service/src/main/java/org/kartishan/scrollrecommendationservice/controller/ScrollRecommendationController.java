package org.kartishan.scrollrecommendationservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.scrollrecommendationservice.service.ScrollRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-scroll-recommendation")
@RequiredArgsConstructor
public class ScrollRecommendationController {
    private final ScrollRecommendationService scrollRecommendationService;
    @GetMapping("/user")
    public ResponseEntity<List<UUID>> getRecommendationsForUser(
            @RequestParam(defaultValue = "10") int limit,
            HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId =UUID.fromString(userIdString);
        List<UUID> recommendations = scrollRecommendationService.getPersonalizedScrolls(userId, limit);
        return ResponseEntity.ok(recommendations);
    }
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Hello");
    }
}
