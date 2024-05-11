package org.kartishan.scrolluserlikeservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scroll {
    private UUID id;
    private String name;
    private String cfiRange;
    private UUID bookId;
    private UUID userId;
}

