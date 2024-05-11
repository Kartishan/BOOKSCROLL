package org.kartishan.scrolluserviewhistoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScrollViewHistoryDTO {
    private UUID userId;
    private UUID scrollId;
    private Date viewTime;
}
