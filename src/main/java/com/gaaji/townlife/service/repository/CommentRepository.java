package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

public interface CommentRepository extends JpaRepository<Comment, String> {
    Stream<Comment> findByUserIdOrderByIdDesc(@NonNull String userId);
}
