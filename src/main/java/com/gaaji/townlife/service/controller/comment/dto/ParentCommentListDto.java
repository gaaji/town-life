package com.gaaji.townlife.service.controller.comment.dto;

import com.gaaji.townlife.service.domain.comment.ParentComment;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ParentCommentListDto {
    private String id;
    private String commenterId;
    private String imageSrc;
    private String location;
    private String text;
    private String postId;
    private LocalDateTime deletedAt;

    public static ParentCommentListDto create(ParentComment entity) {
        ParentCommentListDto dto = new ParentCommentListDto();
        dto.id = entity.getId();
        dto.commenterId = entity.getUserId();
        dto.imageSrc = entity.getContent().getImageSrc();
        dto.location = entity.getContent().getLocation();
        dto.text = entity.getContent().getText();
        dto.postId = entity.getTownLife().getId();
        dto.deletedAt = entity.getDeletedAt();
        return dto;
    }
}
