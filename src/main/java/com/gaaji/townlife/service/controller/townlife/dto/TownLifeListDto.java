package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
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

    public static TownLifeListDto of(TownLife entity, TownLifeCounter counter) {
        return TownLifeListDto.builder()
                .id(entity.getId())
                .categoryId(entity.getCategory().getId())
                .categoryName(entity.getCategory().getName())
                .authorId(entity.getAuthorId())
                .townId(entity.getTownId())
                .title(entity.getContent().getTitle())
                .createdAt(entity.getCreatedAt())
                .commentCount(counter.getCommentCount())
                .reactionCount(counter.getReactionCount())
                .thumbnailSrc(entity.getThumbnailSrc())
                .build();
    }

}
