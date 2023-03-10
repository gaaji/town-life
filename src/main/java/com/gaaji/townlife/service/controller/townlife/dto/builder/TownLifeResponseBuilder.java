package com.gaaji.townlife.service.controller.townlife.dto.builder;

import com.gaaji.townlife.service.adapter.gaaji.dto.AuthProfileDto;
import com.gaaji.townlife.service.controller.townlife.dto.*;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.reaction.Emoji;
import com.gaaji.townlife.service.domain.reaction.PostReaction;
import com.gaaji.townlife.service.domain.reaction.QuestionReaction;
import com.gaaji.townlife.service.domain.townlife.*;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TownLifeResponseBuilder {

    /*
    TownLifeListResponseDto
     */
    public static TownLifeListResponseDto townLifeListResponseDto(Slice<TownLife> townLives) {
        return new TownLifeListResponseDto(
                townLives.hasNext(),
                convertContentFromEntityList(townLives.getContent())
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
    public static TownLifeDetailDto townLifeDetailDto(TownLife entity, AuthProfileDto authProfileDto) {
        TownLifeDetailDto response = null;
        switch (entity.getCategory().getTownLifeType()) {
            case POST:
                response = postTownLifeDetailDto(entity, authProfileDto, entity.getViewCount(), entity.getReactionCount(), entity.getCommentCount(), entity.getInterestCount());
                break;
            case QUESTION:
                response = questionTownLifeDetailDto(entity, authProfileDto, entity.getViewCount(), entity.getReactionCount(), entity.getCommentCount(), entity.getInterestCount());
                break;
        }
        return response;
    }

    public static TownLifeDetailDto townLifeDetailDto(TownLife entity, AuthProfileDto authProfileDto, TownLifeCounter counter) {
        TownLifeDetailDto response = null;
        switch (entity.getCategory().getTownLifeType()) {
            case POST:
                response = postTownLifeDetailDto(entity, authProfileDto, counter.getViewCount(), counter.getReactionCount(), counter.getCommentCount(), counter.getInterestCount());
                break;
            case QUESTION:
                response = questionTownLifeDetailDto(entity, authProfileDto, counter.getViewCount(), counter.getReactionCount(), counter.getCommentCount(), counter.getInterestCount());
                break;
        }
        return response;
    }

        private static TownLifeDetailDto.TownLifeDetailDtoBuilder commonTownLifeDetailDto(TownLife entity, AuthProfileDto authProfileDto, int viewCount, int reactionCount, int commentCount, int interestCount) {
            /*
            TownLife ?????? ????????? ?????? ViewCount??? ???????????? entity??? updatedAt??? ???????????? ?????? ???????????????...
            ??????, ????????? ????????? ??????????????? ???????????? ?????????????????????
            ????????? ?????? ??????????????? ????????? ????????? ??? ????????? LocalDateTime??? ?????????
            updatedAt??? createdAt??? ??????????????? ?????????.
             */
            LocalDateTime createdAt = entity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime updatedAt = entity.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS);
            boolean isUpdated = updatedAt.isAfter(createdAt);

            return TownLifeDetailDto.builder()
                    .id(entity.getId())
                    .category(
                            townLifeDetailCategoryDto(entity.getCategory())
                    )
                    .authorId(authProfileDto.getAuthId())
                    .authorNickName(authProfileDto.getNickname())
                    .authorProfilePictureUrl(authProfileDto.getPictureUrl())
                    .authorMannerTemperature(authProfileDto.getMannerTemperature())
                    .townId(entity.getTownId())
                    .townAddress(entity.getTownAddress())
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .isUpdated(isUpdated)
                    .title(entity.getContent().getTitle())
                    .text(entity.getContent().getText())
                    .location(entity.getContent().getLocation())
                    .viewCount(viewCount)
                    .reactionCount(reactionCount)
                    .commentCount(commentCount)
                    .interestCount(interestCount)
                    .attachedImages(
                            attachedImageDtoList(entity)
                    );
        }

        private static TownLifeDetailDto postTownLifeDetailDto(TownLife entity, AuthProfileDto authProfileDto, int viewCount, int reactionCount, int commentCount, int interestCount) {
            return commonTownLifeDetailDto(entity, authProfileDto, viewCount, reactionCount, commentCount, interestCount)
                    .postReactionDtos(
                            postReactionDtoList((PostTownLife) entity)
                    )
                    .questionReactionDtos(null)
                    .build();
        }

            private static List<PostReactionDto> postReactionDtoList(PostTownLife entity) {
                return entity.getReactions().stream()
                        .map(TownLifeResponseBuilder::postReactionDto)
                        .collect(Collectors.toList());
            }

            private static PostReactionDto postReactionDto(PostReaction entity) {
                return new PostReactionDto(entity.getUserId(), entity.getEmoji());
            }


        private static TownLifeDetailDto questionTownLifeDetailDto(TownLife entity, AuthProfileDto authProfileDto, int viewCount, int reactionCount, int commentCount, int interestCount) {
            return commonTownLifeDetailDto(entity, authProfileDto, viewCount, reactionCount, commentCount, interestCount)
                    .postReactionDtos(null)
                    .questionReactionDtos(
                            questionReactionDtoList((QuestionTownLife) entity)
                    )
                    .build();
        }

            private static List<QuestionReactionDto> questionReactionDtoList(QuestionTownLife entity) {
                return entity.getReactions().stream()
                        .map(TownLifeResponseBuilder::questionReactionDto)
                        .collect(Collectors.toList());
            }

            private static QuestionReactionDto questionReactionDto(QuestionReaction entity) {
                return new QuestionReactionDto(entity.getUserId());
            }

        private static TownLifeDetailCategoryDto townLifeDetailCategoryDto(Category entity) {
            return new TownLifeDetailCategoryDto(entity.getId(), entity.getName());
        }

    /*
    List<AttachedImageDto>
     */
    public static List<AttachedImageDto> attachedImageDtoList(TownLife townLife) {
        return townLife.getAttachedImages().stream()
                .map(TownLifeResponseBuilder::attachedImageDto)
                .sorted(Comparator.comparing(AttachedImageDto::getOrderIndex))
                .collect(Collectors.toList());
    }
        private static AttachedImageDto attachedImageDto(AttachedImage entity) {
            return new AttachedImageDto(entity.getId(), entity.getOrderIndex(), entity.getSrc());
        }

    /*
    ReactionDoResponseDto
     */
    public static ReactionDoResponseDto questionReactionDoResponseDto(String townLifeId, String userId) {
        return ReactionDoResponseDto.builder()
                .townLifeId(townLifeId)
                .userId(userId)
                .build();
    }

    public static ReactionDoResponseDto postReactionDoResponseDto(String townLifeId, String userId, Emoji emoji) {
        return ReactionDoResponseDto.builder()
                .townLifeId(townLifeId)
                .userId(userId)
                .emoji(emoji)
                .build();
    }

}
