package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.reaction.QuestionReaction;
import com.gaaji.townlife.service.domain.reaction.Reaction;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue("question")
public class QuestionTownLife extends TownLife {
    @OneToMany(mappedBy = "questionTownLife", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionReaction> reactions = new ArrayList<>();

    public QuestionTownLife(String authorId, String townId, String title, String text, String location) {
        super(authorId, townId, title, text, location);
    }

    public static QuestionTownLife create(TownLifeSaveRequestDto dto) {
        return new QuestionTownLife(
                dto.getAuthorId(),
                dto.getTownId(),
                dto.getTitle(), dto.getText(), dto.getLocation()
        );
    }

    @Override
    public <T extends Reaction> T addReaction(T reaction) {
        this.reactions.add((QuestionReaction) reaction);
        reaction.associateTownLife(this);

        this.townLifeCounter.doReaction();
        return reaction;
    }

    @Override
    public void removeReactionByUserId(String userId) {
        Optional<QuestionReaction> reactionOpt = this.reactions.stream()
                .filter(r -> Objects.equals(r.getUserId(), userId))
                .findFirst();

        if (reactionOpt.isPresent()) {

            QuestionReaction reaction = reactionOpt.get();
            this.reactions.remove(reaction);
            reaction.associateTownLife(null);

            this.townLifeCounter.cancelReaction();

        } else {
            throw new ResourceRemoveException(ApiErrorCode.REACTION_BY_USER_NOT_FOUND);
        }
    }

}
