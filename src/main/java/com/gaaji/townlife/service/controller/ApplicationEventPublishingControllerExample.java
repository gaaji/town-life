package com.gaaji.townlife.service.controller;

import com.gaaji.townlife.service.applicationservice.ApplicationEventPublishingServiceExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventPublishingControllerExample {
    private final ApplicationEventPublishingServiceExample service;

    @GetMapping("/test/publish-event")
    @ResponseStatus(HttpStatus.OK)
    public void testPublishEvent() {
        log.info("/test/publish-event");
        service.test();
    }
}
