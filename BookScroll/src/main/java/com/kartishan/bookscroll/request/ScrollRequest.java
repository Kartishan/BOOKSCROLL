package com.kartishan.bookscroll.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ScrollRequest {
    private UUID bookId;
    private String name;
    private String cfiRange;
}
