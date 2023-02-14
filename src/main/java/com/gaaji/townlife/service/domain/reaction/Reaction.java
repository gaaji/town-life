package com.gaaji.townlife.service.domain.reaction;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Reaction {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    protected TownLife townLife;
    private String userId;
}
