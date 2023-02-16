package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.adapter.kafka.KafkaProducer;
import com.gaaji.townlife.service.event.dto.NotificationEventBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
    public void handlePostEdited(PostEditedEvent event) {
        // 게시글이 수정되면 그 게시글을 구독중인 사용자에게 알림이 가야 한다.
        kafkaProducer.produceEvent(event);
        kafkaProducer.produceEvent(new NotificationEvent(this, NotificationEventBody.of("게시글 변경됐네요~ 확인해봐요", "id1", "id2", "id3")));
    }
}
