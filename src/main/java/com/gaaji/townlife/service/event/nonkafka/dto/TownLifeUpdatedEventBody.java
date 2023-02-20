package com.gaaji.townlife.service.event.nonkafka.dto;

import com.gaaji.townlife.service.domain.townlife.TownLifeSubscription;
import lombok.Getter;

import java.util.List;

@Getter
public class TownLifeUpdatedEventBody {

    private String townLifeId;
    private String authorId;
    private List<TownLifeSubscription> subscriptions;

    public TownLifeUpdatedEventBody(String townLifeId, String authorId, List<TownLifeSubscription> subscriptions) {
        this.townLifeId = townLifeId;
        this.authorId = authorId;
        this.subscriptions = List.copyOf(subscriptions);
    }

}
