package org.kartishan.bookviewcountservice.feign;

import org.kartishan.bookviewcountservice.model.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "book-service")
public interface BookServiceClient {
    @PostMapping("/api/book/batch")
    List<BookDTO> getBooksByIds(@RequestBody List<UUID> bookIds);
}
