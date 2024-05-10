package org.kartishan.scrolluserviewhistoryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_scroll_view_history")
@Entity
public class UserScrollViewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID userId;

    private UUID scrollId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "view_time")
    private Date viewTime;
}