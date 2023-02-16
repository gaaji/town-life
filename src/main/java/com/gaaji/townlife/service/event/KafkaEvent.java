package com.gaaji.townlife.service.event;

public interface KafkaEvent<T> extends Event{
    String getTopic();
    T getBody();
}
