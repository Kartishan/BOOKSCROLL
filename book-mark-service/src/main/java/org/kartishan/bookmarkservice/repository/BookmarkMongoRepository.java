package org.kartishan.bookmarkservice.repository;

import org.kartishan.bookmarkservice.model.BookmarkDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookmarkMongoRepository extends MongoRepository<BookmarkDocument, String> {
}
