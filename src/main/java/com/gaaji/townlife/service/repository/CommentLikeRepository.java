package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, String> {
    boolean existsByUserIdAndComment(String userId, Comment comment);
}
