package com.gaaji.townlife.service.domain.townlife;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TownLifeContent {
    private String title;
    private String text;
    private String location;

    public static TownLifeContent of(String title, String text, String location) {
        return new TownLifeContent(title, text, location);
    }
}
