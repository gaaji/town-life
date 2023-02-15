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
@Table(indexes = {
        @Index(name = "idx__category_subscription__user_id", columnList = "userId"),
})
public class CategorySubscription {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String userId;
}