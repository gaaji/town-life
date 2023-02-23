package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.CommentLike;
import com.gaaji.townlife.service.event.CommentLikeCreatedEvent;
import com.gaaji.townlife.service.event.CommentLikeDeletedEvent;
import com.gaaji.townlife.service.event.dto.CommentLikeEventBody;
import com.gaaji.townlife.service.repository.CommentLikeRepository;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {
    private final TownLifeRepository townLifeRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public void like(String authId, String townLifeId, String commentId) {
        checkTownLifeExists(townLifeId);
        Comment comment = findCommentById(commentId);

        if(commentLikeRepository.existsByUserIdAndComment(authId, comment)) return;

        CommentLike savedLike = commentLikeRepository.save(CommentLike.create(authId));
        savedLike.associate(comment);

        eventPublisher.publishEvent(new CommentLikeCreatedEvent(this, CommentLikeEventBody.of(savedLike)));
    }

    @Override
    public void unlike(String authId, String townLifeId, String commentId) {
        checkTownLifeExists(townLifeId);
        Comment comment = findCommentById(commentId);
        Optional<CommentLike> likeOptional = commentLikeRepository.findByUserIdAndComment(authId, comment);
        if(likeOptional.isEmpty()) return;
        CommentLike commentLike = likeOptional.get();
        commentLikeRepository.delete(commentLike);
        eventPublisher.publishEvent(new CommentLikeDeletedEvent(this, CommentLikeEventBody.of(commentLike)));
        commentLike.unAssociate(comment);
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
