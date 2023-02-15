package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.service.controller.comment.dto.CommentLikeRequestDto;

public interface CommentLikeService {

    void like(String townLifeId, String commentId, CommentLikeRequestDto dto);
    void unlike(String townLifeId, String commentId);

}
