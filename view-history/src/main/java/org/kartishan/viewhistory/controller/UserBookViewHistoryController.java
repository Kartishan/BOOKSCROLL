package org.kartishan.viewhistory.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.viewhistory.model.dto.BookViewHistoryDTO;
import org.kartishan.viewhistory.service.UserBookViewHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class UserBookViewHistoryController {
    private final UserBookViewHistoryService userBookViewHistoryService;

    @GetMapping("/user")
    public ResponseEntity<List<BookViewHistoryDTO>> getUserHistory(HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }
        System.out.println("userId: " + userId);
        List<BookViewHistoryDTO> history = userBookViewHistoryService.getUserHistory(userId);
        return ResponseEntity.ok(history);
    }
}
