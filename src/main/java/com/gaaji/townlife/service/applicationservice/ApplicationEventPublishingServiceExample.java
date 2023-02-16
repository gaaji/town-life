package com.gaaji.townlife.service.applicationservice;

import com.gaaji.townlife.service.event.PostEditedEvent;
import com.gaaji.townlife.service.event.dto.PostEditedEventBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventPublishingServiceExample {
    private final ApplicationEventPublisher eventPublisher;

    public void test() {
        log.info("ApplicationEventPublishingServiceExample.test()");
        eventPublisher.publishEvent(new PostEditedEvent(this, PostEditedEventBody.of("메뉴 추천좀")));
    }
}
