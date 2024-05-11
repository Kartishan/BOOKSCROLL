package org.kartishan.bookviewcountservice.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
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
