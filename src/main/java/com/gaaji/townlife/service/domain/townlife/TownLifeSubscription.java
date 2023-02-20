package com.gaaji.townlife.service.domain.townlife;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(indexes = {
        @Index(name = "idx__town_life_subscription__user_id", columnList = "userId"),
})
public class TownLifeSubscription {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
    private String userId;

    private TownLifeSubscription(String userId) {
        this.userId = userId;
    }
    public static TownLifeSubscription of(String userId) {
        return new TownLifeSubscription(userId);
    }

    public void associateTownLife(TownLife townLife) {
        this.townLife = townLife;
    }
}
