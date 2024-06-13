package org.kartishan.bookchapterservice.controller;

import lombok.RequiredArgsConstructor;
import org.kartishan.bookchapterservice.model.BookChapterDTO;
import org.kartishan.bookchapterservice.service.BookChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-chapters")
public class bookChapterController {
    private final BookChapterService bookChapterService;

    @GetMapping("/{bookId}/{chapterNumber}/summary")
    public ResponseEntity<String> getChapterSummary(@PathVariable UUID bookId, @PathVariable Long chapterNumber){
        String summary = bookChapterService.getChapterSummary(bookId, chapterNumber);
        if (summary != null){
            return ResponseEntity.ok(summary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<List<BookChapterDTO>> getChapters(@PathVariable UUID bookId) {
        List<BookChapterDTO> chaptersDTO = bookChapterService.getChaptersByBookId(bookId);
        if (!chaptersDTO.isEmpty()) {
            return ResponseEntity.ok(chaptersDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
