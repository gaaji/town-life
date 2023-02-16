package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.reaction.PostReaction;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue("post")
public class PostTownLife extends TownLife {
    @OneToMany(mappedBy = "postTownLife")
    private List<PostReaction> reactions = new ArrayList<>();

    private PostTownLife(String authorId, String townId, TownLifeContent content) {
        super(authorId, townId, content);
    }

    public static PostTownLife create(TownLifeSaveRequestDto dto) {
        return new PostTownLife(
                dto.getAuthorId(),
                dto.getTownId(),
                TownLifeContent.of(dto.getTitle(), dto.getText(), dto.getLocation())
        );
    }
}
