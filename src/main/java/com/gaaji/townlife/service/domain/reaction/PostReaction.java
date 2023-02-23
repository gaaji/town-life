package com.gaaji.townlife.service.domain.reaction;

import com.gaaji.townlife.service.domain.townlife.PostTownLife;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@DiscriminatorValue("post")
public class PostReaction extends Reaction {
    @Enumerated(EnumType.STRING)
    private Emoji emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostTownLife postTownLife;

    private PostReaction(String userId, Emoji emoji) {
        this.userId = userId;
        this.emoji = emoji;
    }

    public static PostReaction of(String userId, Emoji emoji) {
        return new PostReaction(userId, emoji);
    }

    @Override
    public TownLife getTownLife() {
        return this.postTownLife;
    }

    @Override
    public <T extends TownLife> void associateTownLife(T townLife) {
        this.postTownLife = (PostTownLife) townLife;
    }

}
