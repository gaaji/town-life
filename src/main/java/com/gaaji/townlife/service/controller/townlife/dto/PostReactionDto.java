package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.reaction.Emoji;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostReactionDto {

    private String userId;
    private Emoji emoji;

}
