package com.gaaji.townlife.service.event;

import com.gaaji.townlife.service.event.dto.NotificationEventBody;

public class NotificationEvent extends BaseEvent<NotificationEventBody> {
    public NotificationEvent(Object source, NotificationEventBody body) {
        super(source, "noti-server", body);
    }
}
