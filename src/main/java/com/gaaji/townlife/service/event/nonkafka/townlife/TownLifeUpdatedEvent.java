package com.gaaji.townlife.service.event.nonkafka.townlife;

import com.gaaji.townlife.service.event.NonKafkaEventBase;
import com.gaaji.townlife.service.event.nonkafka.dto.TownLifeUpdatedEventBody;

public class TownLifeUpdatedEvent extends NonKafkaEventBase<TownLifeUpdatedEventBody> {
    public TownLifeUpdatedEvent(Object source, TownLifeUpdatedEventBody body) {
        super(source, body);
    }
}
