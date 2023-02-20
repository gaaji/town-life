package com.gaaji.townlife.service.domain.category;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(indexes = {
        @Index(name = "idx__category_subscription__user_id", columnList = "userId"),
})
public class CategoryUnsubscription {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    private String userId;

    private CategoryUnsubscription(String userId) {
        this.userId = userId;
    }

    public static CategoryUnsubscription of(String userId) {
        return new CategoryUnsubscription(userId);
    }

    public void associateCategory(Category category) {
        this.category = category;
    }
}