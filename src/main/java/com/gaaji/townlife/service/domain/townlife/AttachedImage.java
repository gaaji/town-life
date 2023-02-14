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
public class AttachedImage {
    @Id
    @GenericGenerator()
    @GeneratedValue
    private String id;
    private int orderIndex;
    private String src;
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
}
