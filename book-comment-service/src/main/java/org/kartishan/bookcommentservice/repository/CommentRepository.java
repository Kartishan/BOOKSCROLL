package org.kartishan.bookcommentservice.repository;

import org.kartishan.bookcommentservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<Comment> findById(UUID id);
    List<Comment> findAllCommentsByParentComment(Comment parentComment);
    List<Comment> findByBookId(UUID bookId);
}