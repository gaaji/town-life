package com.gaaji.townlife.service.event.dto;

import com.gaaji.townlife.service.domain.comment.CommentLike;
import lombok.Getter;

@Getter
public class CommentLikeEventBody {
    private String userId;
    private String commentId;

    public static CommentLikeEventBody of(CommentLike like) {
        CommentLikeEventBody b = new CommentLikeEventBody();
        b.userId = like.getUserId();
        b.commentId = like.getComment().getId();
        return b;
    }
}
