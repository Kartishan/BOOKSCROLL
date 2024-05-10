package org.kartishan.ratingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kartishan.ratingservice.RabbitMQProducer.RabbitMQProducer;
import org.kartishan.ratingservice.model.UserBookPreference;
import org.kartishan.ratingservice.model.dto.RatingUpdateMessage;
import org.kartishan.ratingservice.repository.UserBookPreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final UserBookPreferenceRepository userBookPreferenceRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final ObjectMapper objectMapper;

    public OptionalDouble calculateAverageRatingForBook(UUID bookID){
        List<UserBookPreference> userBookPreferences = userBookPreferenceRepository.findByBookId(bookID);
        return userBookPreferences.stream()
                .mapToInt(UserBookPreference::getRating)
                .average();
    }

    public void updateUserBookPreference(UUID userId, UUID bookId, int rating){
        UserBookPreference userBookPreference = userBookPreferenceRepository.findByUserIdAndBookId(userId, bookId)
                .orElseGet(()->{
                    UserBookPreference newUserBookPreference = new UserBookPreference();
                    newUserBookPreference.setUserId(userId);
                    newUserBookPreference.setBookId(bookId);
                    return newUserBookPreference;
                });
        userBookPreference.setRating(rating);
        userBookPreferenceRepository.save(userBookPreference);

        OptionalDouble averageRatingOpt = calculateAverageRatingForBook(bookId);
        averageRatingOpt.ifPresent(averageRating -> {
            try {
                RatingUpdateMessage ratingUpdateMessage = new RatingUpdateMessage(bookId, rating);
                String ratingUpdateJson = objectMapper.writeValueAsString(ratingUpdateMessage);
                rabbitMQProducer.sendToQueue("bookRatingQueue", ratingUpdateJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    public List<UserBookPreference> findPreferencesByUser(UUID userId){
        return userBookPreferenceRepository.findByUserId(userId);
    }

    public List<UserBookPreference> findAllPreferences() {
        return userBookPreferenceRepository.findAll();
    }
}
