package com.gaaji.townlife.service.controller.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentSaveRequestDto {
    @NotBlank
    private String commenterId;
    private String location;
    @NotBlank
    private String text;

    private CommentSaveRequestDto(String commenterId, String location, String text) {
        this.commenterId = commenterId;
        this.location = location;
        this.text = text;
    }

    public static CommentSaveRequestDto create(String commenterId, String location, String text) {
        return new CommentSaveRequestDto(commenterId, location, text);
    }
}
