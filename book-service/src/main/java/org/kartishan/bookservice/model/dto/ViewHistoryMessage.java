package org.kartishan.bookservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewHistoryMessage implements Serializable {
    private UUID userId;
    private UUID bookId;
    private Date viewTime;
}
