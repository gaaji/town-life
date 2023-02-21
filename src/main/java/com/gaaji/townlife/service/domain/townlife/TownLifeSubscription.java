package com.gaaji.townlife.service.domain.townlife;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(indexes = {
        @Index(name = "idx__town_life_subscription__user_id", columnList = "userId"),
})
public class TownLifeSubscription {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
    private String userId;

    public static TownLifeSubscription of(String userId) {
        return TownLifeSubscription.builder()
                .userId(userId)
                .build();
    }

    public TownLifeSubscription associateTownLife(TownLife townLife) {
        this.townLife = townLife;
        this.townLife.addSubscription(this);
        return this;
    }
}
