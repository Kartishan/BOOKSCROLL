package org.kartishan.bookimageservice.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kartishan.bookimageservice.model.BookImage;
import org.kartishan.bookimageservice.repository.BookImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {

    private final BookImageRepository bookImageRepository;

    public void uploadBookImage(UUID bookId, MultipartFile file) throws IOException {
        BookImage bookImage = new BookImage();
        bookImage.setBookId(bookId);
        bookImage.setImageData(file.getBytes());
        bookImageRepository.save(bookImage);
    }

    public Optional<BookImage> getBookImage(UUID bookId) {
        return bookImageRepository.findByBookId(bookId);
    }
}