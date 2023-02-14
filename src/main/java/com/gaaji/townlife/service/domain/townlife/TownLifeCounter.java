package com.gaaji.townlife.service.domain.townlife;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class TownLifeCounter {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "commnet_count"))
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
}
