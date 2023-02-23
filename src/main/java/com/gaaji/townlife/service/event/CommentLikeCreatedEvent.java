package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.CommentLikeEventBody;

public class CommentLikeCreatedEvent extends KafkaEventBase<CommentLikeEventBody> {
    public CommentLikeCreatedEvent(Object source, CommentLikeEventBody body) {
        super(source, "townLife-commentLikeCreated", body);
    }
}
