package org.kartishan.bookchapterservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class BookChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Long number;

    @Column(length = 9000)
    private String summary;

    private UUID bookId;
}
