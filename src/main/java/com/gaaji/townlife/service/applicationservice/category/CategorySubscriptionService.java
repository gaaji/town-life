package com.gaaji.townlife.service.applicationservice.category;

public interface CategorySubscriptionService {

    void subscribe(String userId, String categoryId);
    void unsubscribe(String userId, String categoryId);

}
