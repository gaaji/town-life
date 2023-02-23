package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.service.controller.comment.dto.CommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentModifyRequestDto;

public interface CommentModifyService {

    CommentListDto modify(String authId, String townLifeId, String commentId, CommentModifyRequestDto dto);

}
