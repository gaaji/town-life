package com.gaaji.townlife.service.domain.reaction;

import com.gaaji.townlife.service.domain.townlife.QuestionTownLife;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@DiscriminatorValue("question")
public class QuestionReaction extends Reaction {

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionTownLife questionTownLife;

    private QuestionReaction(String userId) {
        this.userId = userId;
    }

    public static QuestionReaction of(String userId) {
        return new QuestionReaction(userId);
    }

    @Override
    public TownLife getTownLife() {
        return this.questionTownLife;
    }

    @Override
    public <T extends TownLife> void associateTownLife(T townLife) {
        this.questionTownLife = (QuestionTownLife) townLife;
    }

}
