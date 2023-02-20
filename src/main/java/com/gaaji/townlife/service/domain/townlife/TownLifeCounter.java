package com.gaaji.townlife.service.domain.townlife;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TownLifeCounter {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "comment_count"))
    private Counter commentCount;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "interest_count"))
    private Counter interestCount;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "reaction_count"))
    private Counter reactionCount;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "view_count"))
    private Counter viewCount;

    public static TownLifeCounter create() {
        return new TownLifeCounter().initCounter();
    }

    private TownLifeCounter initCounter() {
        commentCount = Counter.create();
        interestCount = Counter.create();
        reactionCount = Counter.create();
        viewCount = Counter.create();
        return this;
    }

    public int getCommentCount() { return commentCount.getValue(); }
    public int getInterestCount() { return interestCount.getValue(); }
    public int getReactionCount() { return reactionCount.getValue(); }
    public int getViewCount() { return viewCount.getValue(); }

    public TownLifeCounter view() {
        this.viewCount = this.viewCount.increase();
        return this;
    }

    public TownLifeCounter doReaction() {
        this.reactionCount = this.reactionCount.increase();
        return this;
    }

    public TownLifeCounter cancelReaction() {
        this.reactionCount = this.reactionCount.decrease();
        return this;
    }
}
