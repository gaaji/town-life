package com.gaaji.townlife.service.event.dto;

import com.gaaji.townlife.service.domain.comment.CommentLike;
import lombok.Getter;

@Getter
public class CommentLikeCreatedEventBody {
    private String userId;
    private String commentId;

    public static CommentLikeCreatedEventBody of(CommentLike like) {
        CommentLikeCreatedEventBody b = new CommentLikeCreatedEventBody();
        b.userId = like.getUserId();
        b.commentId = like.getComment().getId();
        return b;
    }
}
