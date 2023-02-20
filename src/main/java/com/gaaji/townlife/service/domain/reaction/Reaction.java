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
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    protected String id;
    protected String userId;

    public abstract TownLife getTownLife();
    public abstract <T extends TownLife> void associateTownLife(T townLife);
}
