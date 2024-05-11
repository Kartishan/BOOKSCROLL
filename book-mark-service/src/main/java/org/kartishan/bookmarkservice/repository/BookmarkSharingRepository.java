package org.kartishan.bookmarkservice.repository;

import org.kartishan.bookmarkservice.model.BookmarkSharing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookmarkSharingRepository extends JpaRepository<BookmarkSharing, UUID> {
}
