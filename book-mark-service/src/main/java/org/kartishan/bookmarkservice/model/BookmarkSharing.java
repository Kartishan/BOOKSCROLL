package org.kartishan.bookmarkservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "bookmark_sharing")
public class BookmarkSharing {
    @Id
    private UUID id;
    private String bookMarkId;
    private UUID bookId;
}
