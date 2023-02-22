package com.gaaji.townlife.service.event;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.adapter.kafka.KafkaProducer;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.event.dto.NotificationEventBody;
import com.gaaji.townlife.service.event.dto.PostEditedEventBody;
import com.gaaji.townlife.service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
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
    private final CommentRepository commentRepository;

    @EventListener
    public void handleToKafkaEvent(KafkaEvent<?> event) {
        kafkaProducer.produceEvent(event);
    }

    @EventListener
    public void handlePostEdited(PostEditedEvent event) {
        PostEditedEventBody editedEventBody = event.getBody();
        String message = "구독 중인 게시글이 수정되었습니다." +System.lineSeparator() +
                editedEventBody.getBefore() + System.lineSeparator() +
                " -> " + editedEventBody.getAfter();
        // TODO 게시물 구독자들을 조회하여 NotificationEventBody에 넣어야 한다.
        kafkaProducer.produceEvent(new NotificationEvent(event.getSource(), NotificationEventBody.of(message, "some subscription id")));
    }

    @EventListener
    public void handleCommentLikeCreated(CommentLikeCreatedEvent event) {
        Comment comment = commentRepository.findById(event.getBody().getCommentId()).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.COMMENT_NOT_FOUND));
        if(comment.getUserId().equals(event.getBody().getUserId())) return; // 본인 댓글에 좋아요 누르면 알림을 보내지 않는다.
        kafkaProducer.produceEvent(new NotificationEvent(event.getSource(), NotificationEventBody.of("다른 사용자가 회원님의 댓글을 좋아합니다.", comment.getUserId())));
    }
}
