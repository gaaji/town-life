package com.gaaji.townlife.service.domain.reaction;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(indexes = {
        @Index(name = "idx__reaction__user_id", columnList = "userId"),
})
public abstract class Reaction {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    private String userId;

    public abstract TownLife getTownLife();
}
