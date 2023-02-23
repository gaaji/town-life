package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, String> {
    Optional<CommentLike> findByUserIdAndComment(String userId, Comment comment);
    boolean existsByUserIdAndComment(String userId, Comment comment);
}
