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
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
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
    @OneToOne(fetch = FetchType.LAZY)
    private TownLife townLife;

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

    public void associateTownLife(TownLife townLife) {
        this.townLife = townLife;
    }

    public TownLifeCounter view() {
        this.viewCount = this.viewCount.increase();
        return this;
    }

}
