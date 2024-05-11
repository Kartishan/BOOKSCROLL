package org.kartishan.scrollservice.feign;

import org.kartishan.scrollservice.model.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "book-service")
public interface BookServiceClient {
    @GetMapping("/api/book/{bookId}")
    BookDTO getBookById(@PathVariable("bookId") UUID bookId);
}
