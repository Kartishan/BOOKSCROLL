package org.kartishan.bookchapterservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookChapterDTO {
    private Long number;
    private String summary;
}
