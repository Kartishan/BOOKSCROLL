package org.kartishan.recommendationservice.controller;

import lombok.RequiredArgsConstructor;
import org.kartishan.recommendationservice.model.BookDTO;
import org.kartishan.recommendationservice.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;
    @GetMapping("/forBook")
    public ResponseEntity<List<BookDTO>> recommendSimilarBooks(@RequestParam UUID bookId, @RequestParam(defaultValue = "5") int n) {
        List<BookDTO> recommendedBooks = recommendationService.recommendSimilarBooks(bookId, n);
        return ResponseEntity.ok(recommendedBooks);
    }
}
