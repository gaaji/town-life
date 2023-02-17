package com.gaaji.townlife.service.event.dto;

import com.gaaji.townlife.service.event.PostEditedEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NotificationEventBody {
    private List<String> users = new ArrayList<>();
    private String message = "";

    public static NotificationEventBody of(String message, String... users) {
        NotificationEventBody body = new NotificationEventBody();
        body.users.addAll(List.of(users));
        return body;
    }

    public static NotificationEventBody of(PostEditedEvent event) {
        NotificationEventBody body = new NotificationEventBody();
        PostEditedEventBody editedEventBody = event.getBody();
        body.message = "구독 중인 게시글이 수정되었습니다." +System.lineSeparator() +
                editedEventBody.getBefore() + System.lineSeparator() +
                " -> " + editedEventBody.getAfter();
        return body;
    }
}
