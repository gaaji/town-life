package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.PostEditedEventBody;

public class PostEditedEvent extends KafkaEventBase<PostEditedEventBody> {
    public PostEditedEvent(Object source, PostEditedEventBody body) {
        super(source, "townLife-townLifeUpdated", body);
    }
}
