package com.gaaji.townlife.service.event;

public interface NonKafkaEvent<T> extends Event {
    T getBody();
}
