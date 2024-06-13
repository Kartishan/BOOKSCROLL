package org.kartishan.bookcommentservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("auth-service")
public interface UserClient {
    @GetMapping("/api/auth/username/{userId}")
    String getUsernameById(@PathVariable("userId") UUID userId);
}
