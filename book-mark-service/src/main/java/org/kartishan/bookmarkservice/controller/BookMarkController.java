package org.kartishan.bookmarkservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.bookmarkservice.model.BookmarkDocument;
import org.kartishan.bookmarkservice.model.dto.BookmarkDTO;
import org.kartishan.bookmarkservice.service.BookMarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

//TODO Надо будет разделить на сервисы, и подумать как выделять userId из заголовка
@RestController
@RequestMapping("/api/book-mark")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @PostMapping("/add/{bookId}")
    public ResponseEntity<?> createBookmark(@RequestBody BookmarkDTO newBookmarkDTO, @PathVariable UUID bookId, HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated");
        }
        bookMarkService.addOrUpdateBookmark(userId, bookId, newBookmarkDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Заметка была добавлена.");
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<List<BookmarkDocument.Bookmark>> getAllBookmarks(@PathVariable UUID bookId, HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated");
        }
        List<BookmarkDocument.Bookmark> bookmarks = bookMarkService.getBookmarks(userId, bookId);
        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/{markId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable UUID bookId,@PathVariable UUID markId,
                                               HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated");
        }
        try {
            bookMarkService.removeBookmark(userId, bookId,markId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/share/{bookId}")
    public ResponseEntity<?> shareBookmarks(@PathVariable UUID bookId, HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated");
        }
        try {
            UUID token = bookMarkService.shareBookmarks(userId, bookId);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/import/{tokenBookMarks}")
    public ResponseEntity<?> importBookmarks(@PathVariable UUID tokenBookMarks,
                                             HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated");
        }
        try {
            bookMarkService.importBookmarks(userId, tokenBookMarks);
            return ResponseEntity.ok().body("Bookmarks imported successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
