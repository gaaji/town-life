package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.CommentLikeCreatedEventBody;

public class CommentLikeCreatedEvent extends KafkaEventBase<CommentLikeCreatedEventBody> {
    public CommentLikeCreatedEvent(Object source, CommentLikeCreatedEventBody body) {
        super(source, "townLife-commentLikeCreated", body);
    }
}
