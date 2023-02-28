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
    private TownLifeDetailCategoryDto category;
    private String authorId;
    private String authorNickName;
    private String authorProfilePictureUrl;
    private double authorMannerTemperature;
    private String townId;
    private String townAddress;
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
