package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.comment.ChildComment;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Stream;

public interface ChildCommentRepository extends JpaRepository<ChildComment, String> {
    Stream<ChildComment> findByParentAndIdGreaterThanOrderByIdAsc(@NonNull ParentComment parent, @NonNull String id, @NonNull Pageable pageable);
    List<ChildComment> findByParentOrderByIdAsc(@NonNull ParentComment parent, @NonNull Pageable pageable);
}
