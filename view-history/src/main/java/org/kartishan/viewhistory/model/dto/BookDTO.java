package org.kartishan.viewhistory.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private UUID id;
    private String name;
    private String author;
    private String authorFull;
    private String description;
    private double rating;
    private Integer pageCount;
    private Set<String> categories;
}
