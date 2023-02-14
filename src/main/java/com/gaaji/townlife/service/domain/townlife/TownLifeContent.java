package com.gaaji.townlife.service.domain.townlife;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class TownLifeContent {
    private String location;
    private String title;
    private String text;
    @OneToMany(mappedBy = "townLife", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AttachedImage> attachedImages = new ArrayList<>();
}
