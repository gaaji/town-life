package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.event.dto.CategoryCreatedEventBody;

public class CategoryCreatedEvent extends BaseEvent<CategoryCreatedEventBody> {
    public CategoryCreatedEvent(Object source, Category category) {
        super(source, "townLife-categoryCreated", new CategoryCreatedEventBody(category));
    }
}
