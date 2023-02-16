package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.reaction.QuestionReaction;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@DiscriminatorValue("question")
public class QuestionTownLife extends TownLife {
    @OneToMany(mappedBy = "questionTownLife")
    private List<QuestionReaction> reactions = new ArrayList<>();

    private QuestionTownLife(String authorId, String townId, TownLifeContent content) {
        super(authorId, townId, content);
    }

    public static QuestionTownLife create(TownLifeSaveRequestDto dto) {
        return new QuestionTownLife(
                dto.getAuthorId(),
                dto.getTownId(),
                TownLifeContent.of(dto.getTitle(), dto.getText(), dto.getLocation())
        );
    }
}
