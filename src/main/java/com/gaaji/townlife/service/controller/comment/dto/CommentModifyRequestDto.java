package com.gaaji.townlife.service.controller.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentModifyRequestDto {
    private String location;
    @NotBlank
    private String text;

    public static CommentModifyRequestDto create(String text, String location) {
        CommentModifyRequestDto dto = new CommentModifyRequestDto();
        dto.text = text;
        dto.location = location;
        return dto;
    }
}
