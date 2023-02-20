package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.service.controller.comment.dto.CommentSaveRequestDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentSaveResponseDto;

public interface CommentSaveService {

    CommentSaveResponseDto saveParent(String authId, String townLifeId, CommentSaveRequestDto dto);
    CommentSaveResponseDto saveChild(String townLifeId, String parentCommentId, CommentSaveRequestDto dto);

}
