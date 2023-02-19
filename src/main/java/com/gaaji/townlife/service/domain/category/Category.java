package com.gaaji.townlife.service.domain.category;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString(exclude = {"unsubscriptions", "townLives"})
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
    @Column(unique = true)
    private String name;
    private boolean defaultCategory;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<CategoryUnsubscription> unsubscriptions = new ArrayList<>();
    @OneToMany(mappedBy = "category")
    private List<TownLife> townLives = new ArrayList<>();
    @Enumerated(value = EnumType.STRING)
    private TownLifeType townLifeType;

    private Category(String name, boolean defaultCategory, String description, TownLifeType townLifeType) {
        this.name = name;
        this.defaultCategory = defaultCategory;
        this.description = description;
        this.townLifeType = townLifeType;
    }

    public static Category create(String name, boolean defaultCategory, String description, TownLifeType townLifeType) {
        return new Category(name, defaultCategory, description, townLifeType);
    }

    public Category addTownLife(TownLife townLife) {
        this.townLives.add(townLife);
        return this;
    }

    // TownLifeType도 수정할 수 있게 하지 않을까
    public void modify(String name, String description, boolean defaultCategory) {
        this.name = name;
        this.defaultCategory = defaultCategory;
        this.description = description;
    }

    public Category addUnsubscription(CategoryUnsubscription categoryUnsubscription) {
        this.unsubscriptions.add(categoryUnsubscription);
        return this;
    }

}
