package org.kartishan.recommendationservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryDTO {
    private UUID id;
    private String name;
}
