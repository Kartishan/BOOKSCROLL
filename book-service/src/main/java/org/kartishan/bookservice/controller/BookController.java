package org.kartishan.bookservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.bookservice.model.Book;
import org.kartishan.bookservice.model.dto.BookDTO;
import org.kartishan.bookservice.request.BookRequest;
import org.kartishan.bookservice.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookByIdWithCategory(@PathVariable UUID id, HttpServletRequest request) {
        BookDTO bookWithCategory = bookService.getBookByIdWithCategory(id);
        return ResponseEntity.ok().body(bookWithCategory);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<BookDTO>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BookDTO> bookPage = bookService.getAllBooks(page, size);
        return ResponseEntity.ok(bookPage);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<BookDTO>> getBooksByCategory(
            @PathVariable("category") String category,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<BookDTO> booksDTOPage = bookService.getBooksByCategory(category, page, pageSize);
        return ResponseEntity.ok(booksDTOPage);
    }

    @GetMapping("/search/partialName/{partialName}")
    public ResponseEntity<Page<Book>> getBooksByPartialName(
            @PathVariable("partialName") String partialName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Book> books = bookService.getBooksByPartialName(partialName, page, size);
        return ResponseEntity.ok(books);
    }
    @PostMapping("/add")
    public ResponseEntity<String> createBooks(@RequestBody BookRequest request){
        bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Книга была добавлена.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> changeBooks(@PathVariable("book") Book book){
        bookService.changeBookInformation(book);
        return ResponseEntity.ok("Книга успешно изменена");
    }
}
