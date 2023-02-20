package com.gaaji.townlife.service.event.nonkafka.dto;

import com.gaaji.townlife.service.domain.townlife.TownLifeSubscription;
import lombok.Getter;

import java.util.List;

@Getter
public class TownLifeEventBody {

    private String issuedUserId;
    private List<TownLifeSubscription> subscriptions;

    private TownLifeEventBody(String issuedUserId, List<TownLifeSubscription> subscriptions) {
        this.issuedUserId = issuedUserId;
        this.subscriptions = List.copyOf(subscriptions);
    }

    public static TownLifeEventBody of(String issuedUserId, List<TownLifeSubscription> subscriptions) {
        return new TownLifeEventBody(issuedUserId, subscriptions);
    }
}
