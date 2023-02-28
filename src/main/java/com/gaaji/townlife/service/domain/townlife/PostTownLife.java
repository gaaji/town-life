package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.service.domain.reaction.PostReaction;
import com.gaaji.townlife.service.domain.reaction.Reaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@DiscriminatorValue("post")
public class PostTownLife extends TownLife {

    @OneToMany(mappedBy = "postTownLife", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReaction> reactions = new ArrayList<>();

    public PostTownLife(String authorId, String townId, String townAddress, String title, String text, String location) {
        super(authorId, townId, townAddress, title, text, location);
    }

    @Override
    public <T extends Reaction> T addReaction(T reaction) {
        this.reactions.add((PostReaction) reaction);
        reaction.associateTownLife(this);

        this.townLifeCounter.doReaction();
        return reaction;
    }

    @Override
    public void removeReactionByUserId(String userId) {
        Optional<PostReaction> reactionOpt = this.reactions.stream()
                .filter(r -> Objects.equals(r.getUserId(), userId))
                .findFirst();

        if (reactionOpt.isPresent()) {

            PostReaction reaction = reactionOpt.get();
            this.reactions.remove(reaction);
            reaction.associateTownLife(null);

            this.townLifeCounter.cancelReaction();

        } else {
            throw new ResourceRemoveException(ApiErrorCode.REACTION_BY_USER_NOT_FOUND);
        }
    }

}
