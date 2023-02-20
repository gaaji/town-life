package com.gaaji.townlife.service.controller.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponseDto {
    private String id;
    private String townLifeId;
    private String text;
    private String location;
    private String commenterId;
    private LocalDateTime createdAt;

    public static CommentSaveResponseDto create(String id, String townLifeId, String text, String location, String userId, LocalDateTime createdAt) {
        CommentSaveResponseDto dto = new CommentSaveResponseDto();
        dto.commenterId = userId;
        dto.id = id;
        dto.townLifeId = townLifeId;
        dto.text = text;
        dto.location = location;
        dto.createdAt = createdAt;
        return dto;
    }
}
