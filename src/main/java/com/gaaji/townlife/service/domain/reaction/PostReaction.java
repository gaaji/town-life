package com.gaaji.townlife.service.domain.reaction;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@DiscriminatorValue("post")
public class PostReaction extends Reaction {
    @Enumerated(EnumType.STRING)
    private Emoji emoji;
}
