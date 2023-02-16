package com.gaaji.townlife.service.domain.category;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private List<CategorySubscription> subscriptions = new ArrayList<>();
    @OneToMany(mappedBy = "category")
    private List<TownLife> townLives = new ArrayList<>();

    private Category(String name, boolean isDefault, String description) {
        this.name = name;
        this.isDefault = isDefault;
        this.description = description;
    }

    public static Category create(String name, boolean isDefault, String description) {
        return new Category(name, isDefault, description);
    }

    public Category addTownLife(TownLife townLife) {
        this.townLives.add(townLife);
        return this;
    }
}
