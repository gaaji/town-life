package com.gaaji.townlife.service.controller.townlife.dto.builder;

import com.gaaji.townlife.service.controller.townlife.dto.*;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.reaction.Emoji;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import org.springframework.data.domain.Slice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseDtoBuilder {

    /*
    TownLifeListResponseDto
     */
    public static TownLifeListResponseDto townLifeListResponseDto(Slice<TownLife> townLives) {
        return new TownLifeListResponseDto(
                convertContentFromEntityList(townLives.getContent()),
                townLives.hasNext()
        );
    }
        private static List<TownLifeListDto> convertContentFromEntityList(List<TownLife> townLives) {
            return townLives.stream()
                    .map(townLife -> townLifeListDto(townLife, townLife.getTownLifeCounter()))
                    .collect(Collectors.toList());
        }
        private static TownLifeListDto townLifeListDto(TownLife entity, TownLifeCounter counter) {
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

    /*
    TownLifeDetailDto
     */
    public static TownLifeDetailDto townLifeDetailDto(TownLife entity) {
        return townLifeDetailDto(entity, entity.getViewCount(), entity.getReactionCount(), entity.getCommentCount(), entity.getInterestCount());
    }

    public static TownLifeDetailDto townLifeDetailDto(TownLife entity, TownLifeCounter counter) {
        return townLifeDetailDto(entity, counter.getViewCount(), counter.getReactionCount(), counter.getCommentCount(), counter.getInterestCount());
    }

        private static TownLifeDetailDto townLifeDetailDto(TownLife entity, int viewCount, int reactionCount, int commentCount, int interestCount) {
            return TownLifeDetailDto.builder()
                    .id(entity.getId())
                    .authorId(entity.getAuthorId())
                    .category(
                            townLifeDetailCategoryDto(entity.getCategory())
                    )
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .title(entity.getContent().getTitle())
                    .text(entity.getContent().getText())
                    .location(entity.getContent().getLocation())
                    .viewCount(viewCount)
                    .reactionCount(reactionCount)
                    .commentCount(commentCount)
                    .interestCount(interestCount)
                    .attachedImages(
                            attachedImageDtoList(entity)
                    )
                    .build();
        }

        private static TownLifeDetailCategoryDto townLifeDetailCategoryDto(Category entity) {
            return new TownLifeDetailCategoryDto(entity.getId(), entity.getName());
        }

    /*
    List<AttachedImageDto>
     */
    public static List<AttachedImageDto> attachedImageDtoList(TownLife townLife) {
        return townLife.getAttachedImages().stream()
                .map(AttachedImageDto::of)
                .sorted(Comparator.comparing(AttachedImageDto::getOrderIndex))
                .collect(Collectors.toList());
    }

    /*
    ReactionDoResponseDto
     */
    public static ReactionDoResponseDto reactionDoResponseDto(String townLifeId, String userId) {
        return ReactionDoResponseDto.builder()
                .townLifeId(townLifeId)
                .userId(userId)
                .build();
    }

    public static ReactionDoResponseDto reactionDoResponseDto(String townLifeId, String userId, Emoji emoji) {
        return ReactionDoResponseDto.builder()
                .townLifeId(townLifeId)
                .userId(userId)
                .emoji(emoji)
                .build();
    }

}
