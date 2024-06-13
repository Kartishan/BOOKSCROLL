package org.kartishan.scrollrecommendationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient("scroll-user-like-service")
public interface UserScrollLikeClient {
    @GetMapping("/api/user-scroll-like/user/{userId}")
    List<UUID> getLikedScrollsByUser(@PathVariable("userId") UUID userId);
    @GetMapping("/api/user-scroll-like/users")
    List<UUID> getAllUsers();
}
