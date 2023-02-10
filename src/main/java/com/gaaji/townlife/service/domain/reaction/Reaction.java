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
public class Reaction {
    @Id
    @GenericGenerator()
    @GeneratedValue
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    protected TownLife townLife;
    private String userId;
}
