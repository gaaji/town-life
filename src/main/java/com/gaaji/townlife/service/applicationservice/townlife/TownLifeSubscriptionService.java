package com.gaaji.townlife.service.applicationservice.townlife;

public interface TownLifeSubscriptionService {

    void subscribe(String userId, String townLifeId);
    void unsubscribe(String userId, String townLifeId);

}
