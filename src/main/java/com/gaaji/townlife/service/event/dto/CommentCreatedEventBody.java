package com.gaaji.townlife.service.event.dto;

import com.gaaji.townlife.service.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreatedEventBody {
    private String id;
    private String townLifeId;
    private String text;
    private String location;
    private String commenterId;
    private LocalDateTime createdAt;

    @Builder
    public CommentCreatedEventBody(String id, String townLifeId, String text, String location, String commenterId, LocalDateTime createdAt) {
        this.id = id;
        this.townLifeId = townLifeId;
        this.text = text;
        this.location = location;
        this.commenterId = commenterId;
        this.createdAt = createdAt;
    }

    public static CommentCreatedEventBody of(Comment savedComment) {
        return builder()
                .id(savedComment.getId())
                .commenterId(savedComment.getUserId())
                .townLifeId(savedComment.getTownLife().getId())
                .text(savedComment.getContent().getText())
                .location(savedComment.getContent().getLocation())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }

}
