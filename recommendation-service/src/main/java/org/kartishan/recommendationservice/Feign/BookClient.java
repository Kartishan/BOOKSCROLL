package org.kartishan.recommendationservice.Feign;

import org.kartishan.recommendationservice.model.BookDTO;
import org.kartishan.recommendationservice.model.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/book/{bookId}")
    BookDTO getBookById(@PathVariable("bookId") UUID bookId);

    @GetMapping("/api/book/list/all")
    List<BookDTO> getAllBooks();

    @GetMapping("/api/book/{bookId}/categories")
    Set<CategoryDTO> findCategoriesByBookId(@PathVariable("bookId") UUID bookId);
}
