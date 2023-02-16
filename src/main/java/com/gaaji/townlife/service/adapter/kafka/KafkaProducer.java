package com.gaaji.townlife.service.adapter.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.townlife.service.event.BaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public <T> void produceEvent(BaseEvent<T> baseEvent) {
        kafkaTemplate.send(baseEvent.getTopic(), writeAsString(baseEvent.getBody()));
    }

    private <T> String writeAsString(T body) {
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException from KafkaProducer.writeAsString(MyEvent)" + System.lineSeparator() + "{}", e.getStackTrace());
            throw new RuntimeException(e);
        }
    }
}
