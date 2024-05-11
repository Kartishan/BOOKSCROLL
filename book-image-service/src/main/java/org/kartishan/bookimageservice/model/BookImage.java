package org.kartishan.bookimageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "bookImages")
@Data
public class BookImage {
    @Id
    private String id;
    private UUID bookId;
    private byte[] imageData;

}
