package org.kartishan.recommendationservice.Feign;

import org.kartishan.recommendationservice.model.UserBookPreference;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "rating-service")
public interface RatingServiceClient {

    @GetMapping("/api/rating/user/{userId}")
    List<UserBookPreference> findByUser(@PathVariable("userId") UUID userId);

    @GetMapping("/api/rating/preferences")
    List<UserBookPreference> getAllPreferences();
}
