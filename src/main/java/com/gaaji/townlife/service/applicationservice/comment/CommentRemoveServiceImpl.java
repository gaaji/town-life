package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.NotYourResourceException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentRemoveServiceImpl implements CommentRemoveService {
    private final CommentRepository commentRepository;
    private final TownLifeRepository townLifeRepository;

    @Override
    public void remove(String authId, String townLifeId, String commentId) {
        checkTownLifeExists(townLifeId);
        Comment comment = findCommentById(commentId);
        if(!comment.getUserId().equals(authId))
            throw new NotYourResourceException("요청자가 작성하지 않은 Comment를 remove할 수 없습니다.");

        commentRepository.delete(comment); // soft delete. orphanRemover 옵션에 의해 likes는 삭제된다.
    }

    private void checkTownLifeExists(String townLifeId) {
        if(!townLifeRepository.existsById(townLifeId))
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
    }

    private Comment findCommentById(String commentId) {
        return  commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.COMMENT_NOT_FOUND));
    }
}
