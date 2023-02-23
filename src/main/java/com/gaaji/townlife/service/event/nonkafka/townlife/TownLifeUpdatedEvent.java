package com.gaaji.townlife.service.event.nonkafka.townlife;

import com.gaaji.townlife.service.event.nonkafka.dto.TownLifeEventBody;

public class TownLifeUpdatedEvent extends TownLifeInternalEvent {
    public TownLifeUpdatedEvent(Object source, TownLifeEventBody body) {
        super(source, body);
    }
}
