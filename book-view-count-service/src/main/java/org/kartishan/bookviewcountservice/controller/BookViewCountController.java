package org.kartishan.bookviewcountservice.controller;

import lombok.RequiredArgsConstructor;
import org.kartishan.bookviewcountservice.model.dto.BookDTO;
import org.kartishan.bookviewcountservice.service.BookViewCountService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book-view-count")
@RequiredArgsConstructor
public class BookViewCountController {
    private final BookViewCountService bookViewCountService;

    @GetMapping("/top-viewed")
    public ResponseEntity<Page<BookDTO>> getTopViewedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int pageSize
    ) {
        try {
            Page<BookDTO> booksPage = bookViewCountService.getTopViewedBooks(page, pageSize);
            return ResponseEntity.ok(booksPage);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
