package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.service.controller.comment.dto.ChildCommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.ParentCommentListDto;

import java.util.List;

public interface CommentFindService {

    List<ChildCommentListDto> findChildCommentListByParentCommentId(String parentCommentId, String lastChildCommentId, int size);
    List<CommentListDto> findListByUserId(String userId);
    List<ParentCommentListDto> findParentCommentListByTownLifeId(String townLifeId);

}
