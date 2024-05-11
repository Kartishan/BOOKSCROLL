package org.kartishan.scrolluserlikeservice.feign;

import org.kartishan.scrolluserlikeservice.model.Scroll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "scroll-service")
public interface ScrollClient {

    @GetMapping("/api/scroll/{id}")
    Scroll getScroll(@PathVariable("id") UUID id);

}
