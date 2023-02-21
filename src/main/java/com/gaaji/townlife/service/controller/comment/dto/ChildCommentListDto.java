package com.gaaji.townlife.service.controller.comment.dto;

import com.gaaji.townlife.service.domain.comment.ChildComment;
import lombok.Getter;

@Getter
public class ChildCommentListDto {
    private String id;
    private String commenterId;
    private String imageSrc;
    private String location;
    private String text;
    private String parentId;

    public ChildCommentListDto(String id, String commenterId, String imageSrc, String location, String text, String parentId) {
        this.id = id;
        this.commenterId = commenterId;
        this.imageSrc = imageSrc;
        this.location = location;
        this.text = text;
        this.parentId = parentId;
    }

    public static ChildCommentListDto of(ChildComment c) {
        return new ChildCommentListDto(
                c.getId(),
                c.getUserId(),
                c.getContent().getImageSrc(),
                c.getContent().getLocation(),
                c.getContent().getText(),
                c.getParent().getId()
        );
    }
}
