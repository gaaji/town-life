package com.gaaji.townlife.service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class NonKafkaEventBase<T> extends ApplicationEvent implements NonKafkaEvent<T> {

    protected T body;

    public NonKafkaEventBase(Object source, T body) {
        super(source);
        this.body = body;
    }

}
