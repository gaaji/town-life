package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.BadRequestException;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.comment.dto.ChildCommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.ParentCommentListDto;
import com.gaaji.townlife.service.domain.comment.ChildComment;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import com.gaaji.townlife.service.repository.ChildCommentRepository;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentFindServiceImpl implements CommentFindService {
    private final TownLifeRepository townLifeRepository;
    private final CommentRepository commentRepository;
    private final ChildCommentRepository childCommentRepository;

    @Override
    public List<ChildCommentListDto> findChildCommentListByParentCommentId(String postId, String parentCommentId, String lastChildCommentId, Integer size) {
        if(!townLifeRepository.existsById(postId))
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
        Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.COMMENT_NOT_FOUND));
        if(!(parentComment instanceof ParentComment))
            throw new BadRequestException("required: id of ParentComment | found: id of Comment(abstract type)");
        if (lastChildCommentId == null && size == null) {
            return findChildrenWithoutOffset((ParentComment) parentComment);
        } else if(lastChildCommentId != null && size != null)
            return findChildrenWithOffset((ParentComment) parentComment, lastChildCommentId, size);
        else
            throw new BadRequestException("constraint: (lastChildCommentId == null and size == null) or (lastChildCommentId != null and size != null)");
    }

    private List<ChildCommentListDto> findChildrenWithOffset(ParentComment parentComment, String lastChildCommentId, Integer size) {
        try (Stream<ChildComment> childCommentStream = childCommentRepository.findByParentAndIdGreaterThanOrderByIdAsc(parentComment, lastChildCommentId, PageRequest.of(0, size))) {
            return childCommentStream.map(ChildCommentListDto::of).collect(Collectors.toList());
        }
    }

    private List<ChildCommentListDto> findChildrenWithoutOffset(ParentComment parentComment) {
        try (Stream<ChildComment> childCommentStream = parentComment.getChildren().stream()) {
            return childCommentStream.sorted(Comparator.comparing(Comment::getId)).map(ChildCommentListDto::of).collect(Collectors.toList());
        }
    }

    @Override
    public List<CommentListDto> findListByUserId(String userId) {
        return null;
    }

    @Override
    public List<ParentCommentListDto> findParentCommentListByTownLifeId(String townLifeId) {
        return townLifeRepository.findById(townLifeId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND))
                .getComments().stream()
                .map(ParentCommentListDto::create)
                .collect(Collectors.toList());
    }
}
