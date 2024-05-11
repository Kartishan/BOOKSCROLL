package org.kartishan.scrolluserlikeservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.scrolluserlikeservice.feign.ScrollClient;
import org.kartishan.scrolluserlikeservice.service.UserScrollLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-scroll-like")
@RequiredArgsConstructor
public class UserScrollLikeController {
    private final UserScrollLikeService userScrollLikeService;
    private final ScrollClient scrollClient;
    @GetMapping("/{scrollId}/like-status")
    public ResponseEntity<Boolean> userLike(@PathVariable UUID scrollId, HttpServletRequest request) {
        try {
            String userIdString = request.getHeader("userId");
            UUID userId =UUID.fromString(userIdString);
            return ResponseEntity.ok(userScrollLikeService.userLike(scrollId, userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(false);
        }
    }
    @PutMapping("/like/{scrollId}")
    public ResponseEntity<?> likeScroll(@PathVariable UUID scrollId, HttpServletRequest request) {
        try {
            String userIdString = request.getHeader("userId");
            UUID userId = UUID.fromString(userIdString);
            userScrollLikeService.likeScroll(userId, scrollId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid user id");
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UUID>> getLikedScrollsByUser(@PathVariable UUID userId) {
        List<UUID> likedScrollIds = userScrollLikeService.getLikedScrollIdsByUser(userId);
        return ResponseEntity.ok(likedScrollIds);
    }
}
