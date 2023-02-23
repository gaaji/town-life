package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.NotYourResourceException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.comment.dto.CommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentModifyRequestDto;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.domain.comment.CommentContent;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentModifyServiceImpl implements CommentModifyService {
    private final CommentRepository commentRepository;
    private final TownLifeRepository townLifeRepository;

    @Override
    public CommentListDto modify(String authId, String townLifeId, String commentId, CommentModifyRequestDto dto) {
        checkTownLifeExists(townLifeId);
        Comment comment = findCommentById(commentId);
        if(!comment.getUserId().equals(authId))
            throw new NotYourResourceException("요청자가 작성하지 않은 Comment를 Modify할 수 없습니다.");

        comment.modify(CommentContent.create(dto.getText(), dto.getLocation()));
        return CommentListDto.of(comment);
    }

    private void checkTownLifeExists(String townLifeId) {
        if(!townLifeRepository.existsById(townLifeId))
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
    }

    private Comment findCommentById(String commentId) {
        return  commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.COMMENT_NOT_FOUND));
    }
}
