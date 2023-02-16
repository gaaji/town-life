package com.gaaji.townlife.service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class BaseEvent<T> extends ApplicationEvent {
    protected String topic;
    protected T body;

    public BaseEvent(Object source, String topic, T body) {
        super(source);
        this.topic = topic;
        this.body = body;
    }
}
