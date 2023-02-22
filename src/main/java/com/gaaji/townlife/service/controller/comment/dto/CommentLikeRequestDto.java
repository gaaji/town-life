package com.gaaji.townlife.service.controller.comment.dto;

import lombok.Getter;

@Getter
public class CommentLikeRequestDto {
    private String userId;

    public static CommentLikeRequestDto create(String userId) {
        CommentLikeRequestDto dto = new CommentLikeRequestDto();
        dto.userId = userId;
        return dto;
    }
}
