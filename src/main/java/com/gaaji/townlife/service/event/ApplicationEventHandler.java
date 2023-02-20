package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.adapter.kafka.KafkaProducer;
import com.gaaji.townlife.service.domain.townlife.TownLifeSubscription;
import com.gaaji.townlife.service.event.dto.NotificationEventBody;
import com.gaaji.townlife.service.event.nonkafka.townlife.TownLifeUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
/**
 * ex) 서비스에서 게시글 변경 이벤트를 발생시키면 이 핸들러에서 다음과 같이 처리한다.
 * 1. kafka 에 같은 이벤트를 produce
 * 2. kafka 에 noti-server 이벤트를 produce
 *
 */
public class ApplicationEventHandler {
    private final KafkaProducer kafkaProducer;

    @Async
    @EventListener
    public void handleToKafkaEvent(KafkaEvent<?> event) {
        kafkaProducer.produceEvent(event);
    }

    @Async
    @EventListener
    public void handlePostEdited(PostEditedEvent event) {
        kafkaProducer.produceEvent(new NotificationEvent(this, NotificationEventBody.of(event)));
    }

    @Async
    @EventListener
    public void handleTownLifeUpdated(TownLifeUpdatedEvent event) {

        List<String> subscribedUserIds = event.getBody().getSubscriptions().stream()
                .map(TownLifeSubscription::getUserId)
                .filter(userId -> !Objects.equals(event.getBody().getAuthorId(), userId))
                .collect(Collectors.toList());

        kafkaProducer.produceEvent(
                new NotificationEvent(event.getSource(), NotificationEventBody.of("관심 가진 동네생활 게시글이 수정되었어요!", subscribedUserIds))
        );

    }
}
