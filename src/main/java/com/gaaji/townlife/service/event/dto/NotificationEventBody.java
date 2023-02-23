package com.gaaji.townlife.service.event.dto;

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

    public static NotificationEventBody of(String message, List<String> users) {
        NotificationEventBody body = new NotificationEventBody();
        body.message = message;
        body.users = List.copyOf(users);
        return body;
    }

}
