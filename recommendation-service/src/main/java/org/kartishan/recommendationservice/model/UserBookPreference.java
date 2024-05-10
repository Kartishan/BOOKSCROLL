package org.kartishan.recommendationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBookPreference {
    private UUID id;
    private UUID userId;
    private UUID bookId;
    private int rating;
}
