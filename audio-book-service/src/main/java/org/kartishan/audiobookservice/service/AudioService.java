package org.kartishan.audiobookservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kartishan.audiobookservice.model.AudioBook;
import org.kartishan.audiobookservice.model.AudioChapter;
import org.kartishan.audiobookservice.repository.AudioBookRepository;
import org.kartishan.audiobookservice.repository.AudioChapterRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AudioService {
    private final GridFsService gridFsService;
    private final AudioBookRepository audioBookRepository;
    private final AudioChapterRepository audioChapterRepository;
    public boolean uploadAudioChapter(UUID audioBookId, Long chapterNumber, MultipartFile audioFile) {
        Optional<AudioBook> audioBookOptional = audioBookRepository.findById(audioBookId);

        if (!audioBookOptional.isPresent()) {
            log.error("AudioBook with ID: " + audioBookId + " not found.");
            return false;
        }

        Optional<AudioChapter> chapterOptional = audioChapterRepository.findByAudioBookAndNumber(audioBookOptional.get(), chapterNumber);

        AudioChapter chapter;
        if (chapterOptional.isPresent()) {
            chapter = chapterOptional.get();
            log.info("Updating existing chapter with number: " + chapterNumber + " for audio book with ID: " + audioBookId);
        } else {
            chapter = AudioChapter.builder()
                    .audioBook(audioBookOptional.get())
                    .number(chapterNumber)
                    .build();
            log.info("Creating new chapter with number: " + chapterNumber + " for audio book with ID: " + audioBookId);
        }

        try {
            String fileId = gridFsService.storeFile(audioFile);
            chapter.setAudioFileId(fileId);
            audioChapterRepository.save(chapter);
            log.info("Audio file for chapter " + chapterNumber + " successfully uploaded and associated with audio book with ID: " + audioBookId);
            return true;
        } catch (IOException e) {
            log.error("Failed to upload audio file for chapter " + chapterNumber + ": " + e.getMessage(), e);
            return false;
        }
    }
    public Optional<AudioBook> findByBookId(UUID bookId) {
        return audioBookRepository.findByBookId(bookId);
    }
    public AudioBook addAudioBook(AudioBook audioBook) {
        return audioBookRepository.save(audioBook);
    }
}
