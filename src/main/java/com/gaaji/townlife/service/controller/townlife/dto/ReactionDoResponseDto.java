package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.reaction.Emoji;
import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ReactionDoResponseDto {

    private String townLifeId;
    private String userId;
    private Emoji emoji;

    public static ReactionDoResponseDto of(String townLifeId, String userId) {
        return ReactionDoResponseDto.builder()
                .townLifeId(townLifeId)
                .userId(userId)
                .build();
    }

    public static ReactionDoResponseDto of(String townLifeId, String userId, Emoji emoji) {
        return ReactionDoResponseDto.builder()
                .townLifeId(townLifeId)
                .userId(userId)
                .emoji(emoji)
                .build();
    }

}
