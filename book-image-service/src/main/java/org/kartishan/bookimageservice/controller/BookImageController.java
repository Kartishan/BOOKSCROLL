package org.kartishan.bookimageservice.controller;

import lombok.RequiredArgsConstructor;
import org.kartishan.bookimageservice.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class BookImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadImage(@RequestParam("bookId") UUID bookId, @RequestParam("image") MultipartFile file) {
        try {
            imageService.uploadBookImage(bookId, file);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID bookId) {
        return imageService.getBookImage(bookId)
                .map(bookImage -> ResponseEntity.ok().body(bookImage.getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
