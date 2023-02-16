package com.gaaji.townlife.service.event.dto;

import lombok.Getter;

@Getter
public class PostEditedEventBody {
    private String title;

    public static PostEditedEventBody of(String title) {
        PostEditedEventBody postEditedEventBody = new PostEditedEventBody();
        postEditedEventBody.title = title;
        return postEditedEventBody;
    }
}
