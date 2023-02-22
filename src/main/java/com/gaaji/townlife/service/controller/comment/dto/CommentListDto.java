package com.gaaji.townlife.service.controller.comment.dto;

import com.gaaji.townlife.service.domain.comment.ChildComment;
import com.gaaji.townlife.service.domain.comment.Comment;
import lombok.Getter;

@Getter
public class CommentListDto {
    private String id;
    private String commenterId;
    private String imageSrc;
    private String location;
    private String text;
    private String postId;
    private String parentId;

    public static CommentListDto of(Comment c) {
        CommentListDto dto = new CommentListDto();
        dto.id = c.getId();
        dto.commenterId = c.getUserId();
        dto.imageSrc = c.getContent().getImageSrc();
        dto.location = c.getContent().getLocation();
        dto.text = c.getContent().getText();
        dto.postId = c.getTownLife().getId();
        dto.parentId = null;
        if (c instanceof ChildComment) dto.parentId = ((ChildComment) c).getParent().getId();
        return dto;
    }
}
