package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
