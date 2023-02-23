package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.reaction.Emoji;
import lombok.*;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReactionDoResponseDto {

    private String townLifeId;
    private String userId;
    private Emoji emoji;

}
