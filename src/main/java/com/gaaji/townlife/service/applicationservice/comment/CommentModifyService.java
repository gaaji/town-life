package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.service.controller.comment.dto.CommentModifyRequestDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentModifyResponseDto;

public interface CommentModifyService {

    CommentModifyResponseDto modify(String townLifeId, String commentId, CommentModifyRequestDto dto);

}
