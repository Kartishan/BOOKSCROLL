package org.kartishan.viewhistory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BookViewHistoryDTO extends BookDTO {
    private Date viewTime;

    public BookViewHistoryDTO(UUID id, String name, String author, String authorFull,
                              String description, double rating, Integer pageCount,
                              Set<String> categories, Date viewTime) {
        super(id, name, author, authorFull, description, rating, pageCount, categories);
        this.viewTime = viewTime;
    }
}