package org.kartishan.bookimageservice.repository;

import org.kartishan.bookimageservice.model.BookImage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookImageRepository extends MongoRepository<BookImage, String> {
    Optional<BookImage> findByBookId(UUID bookId);
}
