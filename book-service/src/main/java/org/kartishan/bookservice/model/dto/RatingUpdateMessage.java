package org.kartishan.bookservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingUpdateMessage {
    private UUID bookId;
    private int rating;
}
