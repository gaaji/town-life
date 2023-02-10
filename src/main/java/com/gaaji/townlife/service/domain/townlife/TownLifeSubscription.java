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
public class TownLifeSubscription {
    @Id
    @GenericGenerator()
    @GeneratedValue
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
    private String userId;
}
