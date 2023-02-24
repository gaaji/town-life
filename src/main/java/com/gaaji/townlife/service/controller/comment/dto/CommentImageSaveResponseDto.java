package com.gaaji.townlife.service.controller.comment.dto;

import lombok.Getter;

@Getter
public class CommentImageSaveResponseDto {
    private String commentId;
    private String src;

    public static CommentImageSaveResponseDto create(String commentId, String imageSrc) {
        CommentImageSaveResponseDto dto = new CommentImageSaveResponseDto();
        dto.commentId = commentId;
        dto.src = imageSrc;
        return dto;
    }
}
