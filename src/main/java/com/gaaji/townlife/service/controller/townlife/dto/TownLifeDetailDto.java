package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class TownLifeDetailDto {
    private String id;
    private String authorId;
    private TownLifeDetailCategoryDto category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String text;
    private String location;
    private int viewCount;
    private int reactionCount;
    private int commentCount;
    private int interestCount;

    public static TownLifeDetailDto of(TownLife entity) {
        return TownLifeDetailDto.builder()
                .id(entity.getId())
                .authorId(entity.getAuthorId())
                .category(TownLifeDetailCategoryDto.of(entity.getCategory()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .title(entity.getContent().getTitle())
                .text(entity.getContent().getText())
                .location(entity.getContent().getLocation())
                .build();
    }

    public static TownLifeDetailDto of(TownLife entity, TownLifeCounter counter) {
        return TownLifeDetailDto.builder()
                .id(entity.getId())
                .authorId(entity.getAuthorId())
                .category(TownLifeDetailCategoryDto.of(entity.getCategory()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .title(entity.getContent().getTitle())
                .text(entity.getContent().getText())
                .location(entity.getContent().getLocation())
                .viewCount(counter.getViewCount().getValue())
                .reactionCount(counter.getReactionCount().getValue())
                .commentCount(counter.getCommentCount().getValue())
                .interestCount(counter.getInterestCount().getValue())
                .build();
    }
    
}
