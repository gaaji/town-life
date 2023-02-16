package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.CategoryUpdatedEventBody;

public class CategoryUpdatedEvent extends BaseEvent<CategoryUpdatedEventBody> {
    public CategoryUpdatedEvent(Object source, CategoryUpdatedEventBody body) {
        super(source, "townLife-categoryUpdated", body);
    }
}
