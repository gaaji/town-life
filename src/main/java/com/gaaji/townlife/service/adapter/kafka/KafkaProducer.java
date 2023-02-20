package com.gaaji.townlife.service.adapter.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.townlife.service.event.KafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public <T> void produceEvent(KafkaEvent<T> event) {
        kafkaTemplate.send(event.getTopic(), writeAsString(event.getBody()));
    }

    private <T> String writeAsString(T body) {
        try {
            return new ObjectMapper().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException from KafkaProducer.writeAsString(MyEvent)" + System.lineSeparator() + "{}", Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
}
