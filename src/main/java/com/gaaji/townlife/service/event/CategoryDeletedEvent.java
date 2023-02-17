package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.CategoryDeletedEventBody;

public class CategoryDeletedEvent extends KafkaEventBase<CategoryDeletedEventBody> {
    public CategoryDeletedEvent(Object source, CategoryDeletedEventBody body) {
        super(source, "townLife-categoryCreated", body);
    }
}
