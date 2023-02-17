package com.gaaji.townlife.service.event.dto;

import lombok.Getter;

@Getter
public class PostEditedEventBody {
    private String before;
    private String after;

    public static PostEditedEventBody of(String before, String after) {
        PostEditedEventBody postEditedEventBody = new PostEditedEventBody();
        postEditedEventBody.before = before;
        postEditedEventBody.after = after;
        return postEditedEventBody;
    }
}
