package org.kartishan.audiobookservice.controller;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.kartishan.audiobookservice.model.AudioBook;
import org.kartishan.audiobookservice.service.AudioService;
import org.kartishan.audiobookservice.service.GridFsService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/audio")
@RequiredArgsConstructor
public class AudioController {
    private final AudioService audioService;
    private final GridFsService gridFsService;

    @PostMapping("/upload/{audioBookId}/{chapterNumber}")
    public ResponseEntity<String> uploadAudioChapter(@PathVariable UUID audioBookId,
                                                     @PathVariable Long chapterNumber,
                                                     @RequestParam("file") MultipartFile file) {
        boolean uploadSuccess = audioService.uploadAudioChapter(audioBookId, chapterNumber, file);
        if (uploadSuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("Аудиофайл успешно загружен для главы " + chapterNumber + " аудиокниги с ID: " + audioBookId);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при загрузке аудиофайла для главы " + chapterNumber + " аудиокниги с ID: " + audioBookId);
        }
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        GridFSFile gridFSFile = gridFsService.retrieveFile(fileId);
        if (gridFSFile == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = gridFSFile.getFilename();
        String contentType = Optional.ofNullable(gridFSFile.getMetadata().get("_contentType").toString())
                .orElse("application/octet-stream");

        InputStreamResource resource = gridFsService.downloadFileAsResource(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentLength(gridFSFile.getLength())
                .body(resource);
    }

    @GetMapping("/bookid/{bookId}")
    public ResponseEntity<UUID> getAudioBookIdByBookId(@PathVariable UUID bookId) {
        Optional<AudioBook> audioBookOptional = audioService.findByBookId(bookId);
        if (audioBookOptional.isPresent()) {
            return ResponseEntity.ok(audioBookOptional.get().getId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<AudioBook> addAudioBook(@RequestBody AudioBook audioBook) {
        AudioBook savedAudioBook = audioService.addAudioBook(audioBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAudioBook);
    }
}
