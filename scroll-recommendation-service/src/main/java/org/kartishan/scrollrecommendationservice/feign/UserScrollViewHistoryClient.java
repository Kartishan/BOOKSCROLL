package org.kartishan.scrollrecommendationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient("scroll-user-view-history-service")
public interface UserScrollViewHistoryClient {
    @GetMapping("/api/user-scroll-view-history/user/{userId}")
    List<UUID> getLikedScrollsByUser(@PathVariable("userId") UUID userId);
}
