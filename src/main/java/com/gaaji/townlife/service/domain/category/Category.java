package com.gaaji.townlife.service.domain.category;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@Table(indexes = {
        @Index(name = "idx__category__name", columnList = "name"),
})
public class Category {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    private String name;
    private boolean isDefault;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<CategorySubscription> subscriptions;
    @OneToMany(mappedBy = "category")
    private List<TownLife> townLives;

    public static Category create(String name, boolean isDefault, String description) {
        return Category.builder()
                .name(name)
                .isDefault(isDefault)
                .description(description)
                .subscriptions(new ArrayList<>())
                .townLives(new ArrayList<>())
                .build();
    }
}
