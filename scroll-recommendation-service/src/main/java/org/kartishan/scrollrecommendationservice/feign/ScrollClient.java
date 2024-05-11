package org.kartishan.scrollrecommendationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "scroll-service")
public interface ScrollClient {

    @GetMapping("/api/scroll/random")
    List<UUID> getRandomScrollIds(@RequestParam("count") int count);
}
