package org.kartishan.recommendationservice.service;

import lombok.RequiredArgsConstructor;
import org.kartishan.recommendationservice.Feign.BookClient;
import org.kartishan.recommendationservice.Feign.RatingServiceClient;
import org.kartishan.recommendationservice.model.BookDTO;
import org.kartishan.recommendationservice.model.CategoryDTO;
import org.kartishan.recommendationservice.model.UserBookPreference;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final BookClient bookClient;
    private final RatingServiceClient ratingServiceClient;

    private double calculateSimilarityBetweenBooks(UUID bookId1, UUID bookId2) {
        Set<CategoryDTO> categoriesBook1 = bookClient.findCategoriesByBookId(bookId1);
        Set<CategoryDTO> categoriesBook2 = bookClient.findCategoriesByBookId(bookId2);

        boolean hasCommonCategories = categoriesBook1.stream()
                .anyMatch(cat1 -> categoriesBook2.stream()
                        .anyMatch(cat2 -> cat1.getName().equals(cat2.getName())));

        return hasCommonCategories ? 1.0 : 0.0;
    }

    private double calculatePredictedRatingForBook(UUID bookId, double similarityThreshold) {
        List<UserBookPreference> allPreferences = ratingServiceClient.getAllPreferences();
        double totalRating = 0;
        int count = 0;

        for (UserBookPreference preference : allPreferences) {
            if (!preference.getBookId().equals(bookId)) {
                continue;
            }
            double similarity = calculateSimilarityBetweenBooks(bookId, preference.getBookId());
            if (similarity >= similarityThreshold) {
                totalRating += preference.getRating() * similarity;
                count++;
            }
        }

        return count > 0 ? totalRating / count : 0;
    }

    public List<BookDTO> recommendSimilarBooks(UUID bookId, int n) {
        BookDTO selectedBook = bookClient.getBookById(bookId);
        if (selectedBook == null) {
            return Collections.emptyList();
        }

        List<BookDTO> allBooks = bookClient.getAllBooks();
        Map<BookDTO, Double> predictedRatings = new HashMap<>();

        for (BookDTO otherBook : allBooks) {
            if (!otherBook.getId().equals(bookId)) {
                double similarity = calculateSimilarityBetweenBooks(bookId, otherBook.getId());
                if (similarity > 0) {
                    double predictedRating = calculatePredictedRatingForBook(otherBook.getId(), similarity);
                    predictedRatings.put(otherBook, predictedRating);
                }
            }
        }

        return predictedRatings.entrySet().stream()
                .sorted(Map.Entry.<BookDTO, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(n)
                .collect(Collectors.toList());
    }
}
