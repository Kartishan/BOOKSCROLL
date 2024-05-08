package com.kartishan.bookscroll.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HeroSimpleDTO {
    private UUID id;
    private String name;
}
