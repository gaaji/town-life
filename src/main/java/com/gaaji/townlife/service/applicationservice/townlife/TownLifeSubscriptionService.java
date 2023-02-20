package com.gaaji.townlife.service.applicationservice.townlife;

public interface TownLifeSubscriptionService {

    void subscribe(String townLifeId, String userId);
    void unsubscribe(String townLifeId, String userId);

}
