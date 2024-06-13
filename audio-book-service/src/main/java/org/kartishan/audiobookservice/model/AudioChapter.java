package org.kartishan.audiobookservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audio_chapter")
@Entity
public class AudioChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "audio_book_id", referencedColumnName = "id")
    private AudioBook audioBook;

    private Long number;
    private String audioFileId;
}
