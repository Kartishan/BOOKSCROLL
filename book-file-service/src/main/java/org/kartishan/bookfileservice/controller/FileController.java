package org.kartishan.bookfileservice.controller;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.kartishan.bookfileservice.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/book/upload/{bookId}")
    public ResponseEntity<?> uploadBookFile(@PathVariable UUID bookId, @RequestParam("file") MultipartFile file) {
        try {
            fileService.storeFile(file, bookId);
            return ResponseEntity.ok("Файл книги успешно добавлен");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Не удалось добавить файл для книги");
        }
    }
    @GetMapping("/download/{bookId}")
    public ResponseEntity<?> downloadBookFile(@PathVariable UUID bookId) {
        InputStreamResource inputStreamResource = fileService.downloadFileAsResource(bookId);

        GridFSFile gridFSFile = fileService.retrieveFileByBookId(bookId);
        String fileName = gridFSFile.getFilename();

        String contentType = Optional.ofNullable(gridFSFile.getMetadata().get("_contentType").toString())
                .orElse("application/octet-stream");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(gridFSFile.getLength())
                .body(inputStreamResource);
    }
}
