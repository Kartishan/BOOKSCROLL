package org.kartishan.bookcommentservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kartishan.bookcommentservice.feign.UserClient;
import org.kartishan.bookcommentservice.model.Comment;
import org.kartishan.bookcommentservice.model.dto.CommentDTO;
import org.kartishan.bookcommentservice.repository.CommentRepository;
import org.kartishan.bookcommentservice.request.CommentRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserClient userClient;

    public Comment getCommentById(UUID id){
        try {
            return commentRepository.findById(id).get();
        }catch (Exception e){
            throw new RuntimeException("Комментрий с id: " + id + " не найден");
        }
    }

    public List<Comment> getAllCommentsForBook(UUID bookId) {
        return commentRepository.findByBookId(bookId);
    }

    public List<CommentDTO> getAllCommentsForBookDTO(UUID bookId) {
        System.out.println("bookId: " + bookId);

        List<Comment> comments = commentRepository.findByBookId(bookId);
        comments.forEach(comment -> System.out.println("Comment: " + comment));
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CommentDTO convertToDto(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .title(comment.getTitle())
                .userId(comment.getUserId())
                .username(userClient.getUsernameById(comment.getUserId()))
                .bookId(comment.getBookId())
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .build();
    }

    public List<Comment> getCommentsForParentComment(UUID id){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            return (commentRepository.findAllCommentsByParentComment(comment));
        } else {
            throw new RuntimeException("Ошибка: Комментарий с указанным ID не найден.");
        }
    }
    public void createNewComment(CommentRequest commentRequest, UUID userId) {
        Comment comment = new Comment();
        comment.setBookId(commentRequest.getBookId());

        if (commentRequest.getParentCommentId() != null) {
            Comment parentComment = getCommentById(commentRequest.getParentCommentId());

            while (parentComment.getParentComment() != null) {
                parentComment = parentComment.getParentComment();
            }
            comment.setParentComment(parentComment);
        }

        comment.setUserId(userId);
        comment.setTitle(commentRequest.getTittle());
        commentRepository.save(comment);
    }

}
