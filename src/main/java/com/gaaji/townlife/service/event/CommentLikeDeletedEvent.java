package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.CommentLikeEventBody;

public class CommentLikeDeletedEvent extends KafkaEventBase<CommentLikeEventBody> {
    public CommentLikeDeletedEvent(Object source, CommentLikeEventBody body) {
        super(source, "townLife-commentLikeDeleted", body);
    }
}
