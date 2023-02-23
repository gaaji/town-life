package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TownLifeDetailDto {
    private String id;
    private String authorId;
    private TownLifeDetailCategoryDto category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isUpdated;
    private String title;
    private String text;
    private String location;
    private int viewCount;
    private int reactionCount;
    private int commentCount;
    private int interestCount;
    private List<AttachedImageDto> attachedImages;
    private List<PostReactionDto> postReactionDtos;
    private List<QuestionReactionDto> questionReactionDtos;

}
