package com.gaaji.townlife.service.domain.category;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class CategorySubscription {
    @Id
    @GenericGenerator()
    @GeneratedValue
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String userId;
}