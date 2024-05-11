package org.kartishan.scrollservice.feign;

import org.kartishan.scrollservice.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {
    @GetMapping("/api/auth/user/{userId}")
    UserDTO getUserById(@PathVariable("userId") UUID userId);
}
