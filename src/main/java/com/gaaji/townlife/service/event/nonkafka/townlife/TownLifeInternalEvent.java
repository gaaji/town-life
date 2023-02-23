package com.gaaji.townlife.service.event.nonkafka.townlife;

import com.gaaji.townlife.service.domain.townlife.TownLifeSubscription;
import com.gaaji.townlife.service.event.NonKafkaEventBase;
import com.gaaji.townlife.service.event.nonkafka.dto.TownLifeEventBody;
import lombok.Getter;

import java.util.List;

@Getter
public class TownLifeInternalEvent extends NonKafkaEventBase<TownLifeEventBody> {

    protected String issuedUserId;
    protected List<TownLifeSubscription> subscriptions;

    public TownLifeInternalEvent(Object source, TownLifeEventBody body) {
        super(source, body);
        this.issuedUserId = body.getIssuedUserId();
        this.subscriptions = body.getSubscriptions();
    }
}
