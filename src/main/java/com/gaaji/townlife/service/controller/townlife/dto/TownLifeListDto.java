package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TownLifeListDto {

    private String id;
    private String categoryId;
    private String authorId;
    private String categoryName;
    private String townId;
    private String title;
    private LocalDateTime createdAt;
    private int commentCount;
    private int reactionCount;
    private String thumbnailSrc;

}
