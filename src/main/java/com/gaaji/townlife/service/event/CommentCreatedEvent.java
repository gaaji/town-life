package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.CommentCreatedEventBody;

public class CommentCreatedEvent extends KafkaEventBase<CommentCreatedEventBody> {
    public CommentCreatedEvent(Object source, CommentCreatedEventBody body) {
        super(source, "townLife-commentCreated", body);
    }
}
