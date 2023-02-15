package com.gaaji.townlife.service.applicationservice.category;

import com.gaaji.townlife.service.controller.category.dto.CategorySubscribeDto;
import com.gaaji.townlife.service.controller.category.dto.CategoryUnsubscribeDto;

public interface CategorySubscriptionService {

    void subscribe(String categoryId, CategorySubscribeDto dto);
    void unsubscribe(String categoryId, CategoryUnsubscribeDto dto);

}
