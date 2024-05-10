package org.kartishan.scrollservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.scrollservice.request.ScrollRequest;
import org.kartishan.scrollservice.service.ScrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/scroll")
@RequiredArgsConstructor
public class ScrollController {
    private final ScrollService scrollService;
    @PostMapping("/create")
    public ResponseEntity<?> createScroll(@RequestBody ScrollRequest scrollRequest, HttpServletRequest request){
        try {
            String userIdString = request.getHeader("userId");
            UUID userId = UUID.fromString(userIdString);
            scrollService.createScroll(userId, scrollRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Scroll успешно создан");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getScroll(@PathVariable UUID id){
        try {
            return ResponseEntity.ok(scrollService.getScroll(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scroll not found");
        }
    }
}
