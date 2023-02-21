package com.gaaji.townlife.service.domain.townlife;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class AttachedImage {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    private int orderIndex;
    private String src;
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
}
