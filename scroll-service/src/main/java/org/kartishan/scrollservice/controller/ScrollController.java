package org.kartishan.scrollservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.scrollservice.model.Scroll;
import org.kartishan.scrollservice.model.dto.ScrollDTO;
import org.kartishan.scrollservice.repository.ScrollRepository;
import org.kartishan.scrollservice.request.ScrollRequest;
import org.kartishan.scrollservice.service.ScrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/scroll")
@RequiredArgsConstructor
public class ScrollController {
    private final ScrollService scrollService;
    private final ScrollRepository scrollRepository;

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

    @GetMapping("/full/{id}")
    public ResponseEntity<ScrollDTO> getScrollDetails(@PathVariable UUID id,  HttpServletRequest request) {
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }

        ScrollDTO scrollDTO = scrollService.getScrollDetails(id, userId);
        return ResponseEntity.ok(scrollDTO);
    }

    @GetMapping("/random")
    public ResponseEntity<List<UUID>> getRandomScrollIds(@RequestParam int count) {
        List<Scroll> allScrolls = scrollRepository.findAll();
        Collections.shuffle(allScrolls);
        List<UUID> randomScrollIds = allScrolls.stream()
                .map(Scroll::getId)
                .limit(count)
                .collect(Collectors.toList());
        return ResponseEntity.ok(randomScrollIds);
    }
}
