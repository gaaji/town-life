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
    @GenericGenerator()
    @GeneratedValue
    private String id;
    @Embedded
    private Counter commentCount;
    @Embedded
    private Counter interestCount;
    @Embedded
    private Counter reactionCount;
    @Embedded
    private Counter viewCount;
    @OneToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
}
