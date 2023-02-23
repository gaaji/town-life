package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.BadRequestException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.comment.dto.CommentSaveRequestDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentSaveResponseDto;
import com.gaaji.townlife.service.domain.comment.ChildComment;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.CommentContent;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.event.CommentCreatedEvent;
import com.gaaji.townlife.service.event.dto.CommentCreatedEventBody;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentSaveServiceImpl implements CommentSaveService {
    private final CommentRepository commentRepository;
    private final TownLifeRepository townLifeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public CommentSaveResponseDto saveParent(String authId, String townLifeId, CommentSaveRequestDto dto) {
        checkAuth(authId, dto.getCommenterId());
        TownLife townLife = getTownLifeOrThrow(townLifeId);
        ParentComment savedComment = commentRepository.save(ParentComment.create(townLife, CommentContent.create(dto.getText(), dto.getLocation()), authId));
        savedComment.associate(townLife);
        CommentContent content = savedComment.getContent();

        eventPublisher.publishEvent(new CommentCreatedEvent(this, CommentCreatedEventBody.of(savedComment)));

        return CommentSaveResponseDto.create(
                savedComment.getId(),
                savedComment.getTownLife().getId(),
                content.getText(),
                content.getLocation(),
                savedComment.getUserId(),
                savedComment.getCreatedAt()
        );
    }

    @Override
    public CommentSaveResponseDto saveChild(String authId, String townLifeId, String parentCommentId, CommentSaveRequestDto dto) {
        checkAuth(authId, dto.getCommenterId());
        getTownLifeOrThrow(townLifeId);
        Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.COMMENT_NOT_FOUND));
        if(!(parentComment instanceof ParentComment))
            throw new BadRequestException("required: id of ParentComment | found: id of Comment(abstract type)");
        ParentComment parent = (ParentComment) parentComment;
        ChildComment savedComment = commentRepository.save(ChildComment.create(parent, CommentContent.create(dto.getText(), dto.getLocation()), authId));
        savedComment.associate(parent);
        eventPublisher.publishEvent(new CommentCreatedEvent(this, CommentCreatedEventBody.of(savedComment)));

        return CommentSaveResponseDto.create(
                savedComment.getId(),
                savedComment.getTownLife().getId(),
                savedComment.getContent().getText(),
                savedComment.getContent().getLocation(),
                savedComment.getUserId(),
                savedComment.getCreatedAt()
        );
    }

    private static void checkAuth(String authId, String otherId) {
        if(!authId.equals(otherId))
            throw new BadRequestException("Header.Authorization 과 body.commentId 가 일치해가 일치하지 않습니다.");
    }

    private TownLife getTownLifeOrThrow(String townLifeId) {
        return townLifeRepository.findById(townLifeId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }
}
