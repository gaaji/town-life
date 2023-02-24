package com.gaaji.townlife.service.domain.comment;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CommentContent {
    private String imageSrc = "";
    private String location = "";
    private String text = "";

    public static CommentContent create(String text, String location) {
        CommentContent commentContent = new CommentContent();
        commentContent.text = text;
        commentContent.location = location;
        return commentContent;
    }

    public static CommentContent create(String text, String location, String newSrc) {
        CommentContent c = create(text, location);
        c.imageSrc = newSrc;
        return c;
    }
}
