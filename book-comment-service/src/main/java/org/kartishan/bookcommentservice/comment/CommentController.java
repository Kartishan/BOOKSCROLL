package org.kartishan.bookcommentservice.comment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kartishan.bookcommentservice.model.Comment;
import org.kartishan.bookcommentservice.model.dto.CommentDTO;
import org.kartishan.bookcommentservice.request.CommentRequest;
import org.kartishan.bookcommentservice.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Comment>> getCommentsForParentComment(@PathVariable("id")UUID id){
        List<Comment> comments = commentService.getCommentsForParentComment(id);
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/book/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForBook(@PathVariable("id") UUID id){
        List<CommentDTO> comments = commentService.getAllCommentsForBookDTO(id);
        return ResponseEntity.ok(comments);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest commentRequest, HttpServletRequest request){
        String userIdString = request.getHeader("userId");
        UUID userId = null;
        if (userIdString != null && !userIdString.isEmpty()) {
            try {
                userId = UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID string: " + userIdString);
            }
        }

        commentService.createNewComment(commentRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Комментарий добавлен успешно.");
    }
}
