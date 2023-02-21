package com.gaaji.townlife.service.domain.townlife;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class AttachedImage {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    private int orderIndex;
    private String src;
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;

    private AttachedImage(int orderIndex, String src) {
        this.orderIndex = orderIndex;
        this.src = src;
    }

    public static AttachedImage of(int orderIndex, String src) {
        return new AttachedImage(orderIndex, src);
    }

    public void associateTownLife(TownLife townLife) {
        this.townLife = townLife;
    }

}
