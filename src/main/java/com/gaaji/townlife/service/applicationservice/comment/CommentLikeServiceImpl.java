package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.BadRequestException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.comment.dto.CommentLikeRequestDto;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.CommentLike;
import com.gaaji.townlife.service.event.CommentLikeCreatedEvent;
import com.gaaji.townlife.service.event.dto.CommentLikeCreatedEventBody;
import com.gaaji.townlife.service.repository.CommentLikeRepository;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {
    private final TownLifeRepository townLifeRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public void like(String townLifeId, String commentId, CommentLikeRequestDto dto) {
        checkTownLifeExists(townLifeId);
        Comment comment = findCommentById(commentId);

        if(commentLikeRepository.existsByUserIdAndComment(dto.getUserId(), comment))
            throw new BadRequestException("해당 사용자는 이미 해당 댓글에 좋아요를 표시한 상태입니다.");

        CommentLike savedLike = commentLikeRepository.save(CommentLike.create(dto.getUserId()));
        savedLike.associate(comment);

        eventPublisher.publishEvent(new CommentLikeCreatedEvent(this, CommentLikeCreatedEventBody.of(savedLike)));
    }

    @Override
    public void unlike(String townLifeId, String commentId) {
        // TODO IMPLEMENT
    }

    private Comment findCommentById(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.COMMENT_NOT_FOUND));
        return comment;
    }

    private void checkTownLifeExists(String townLifeId) {
        if(!townLifeRepository.existsById(townLifeId))
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
    }
}
