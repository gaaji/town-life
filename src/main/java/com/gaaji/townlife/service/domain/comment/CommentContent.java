package com.gaaji.townlife.service.domain.comment;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class CommentContent {
    private String imageSrc;
    private String location;
    private String text;
}
