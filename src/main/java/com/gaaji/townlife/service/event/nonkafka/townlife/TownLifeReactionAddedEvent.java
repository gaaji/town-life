package com.gaaji.townlife.service.event.nonkafka.townlife;

import com.gaaji.townlife.service.event.nonkafka.dto.TownLifeEventBody;

public class TownLifeReactionAddedEvent  extends TownLifeInternalEvent {
    public TownLifeReactionAddedEvent(Object source, TownLifeEventBody body) {
        super(source, body);
    }
}
