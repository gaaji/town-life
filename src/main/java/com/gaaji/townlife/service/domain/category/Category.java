package com.gaaji.townlife.service.domain.category;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceAlreadyExistException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceUnmodifiableException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(indexes = {
        @Index(name = "idx__category__name", columnList = "name"),
})
public class Category {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    @Column(unique = true)
    private String name;
    private boolean defaultCategory;
    private String description;
    @OneToMany(mappedBy = "category", orphanRemoval = true)
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

    public void addUnsubscription(CategoryUnsubscription unsubscription) {
        validateCategoryIsDefault();

        validateExistsUnsubscriptionByUserId(unsubscription.getUserId());

        this.unsubscriptions.add(unsubscription);
        unsubscription.associateCategory(this);
    }

    private void validateCategoryIsDefault() {
        if (this.defaultCategory) {
            throw new ResourceUnmodifiableException(ApiErrorCode.CATEGORY_SUBSCRIPTION_UNMODIFIABLE_ERROR);
        }
    }

    private void validateExistsUnsubscriptionByUserId(String userId) {
        Optional<CategoryUnsubscription> unsubscriptionOpt = this.unsubscriptions.stream()
                .filter(s -> Objects.equals(s.getUserId(), userId))
                .findFirst();

        if (unsubscriptionOpt.isPresent()) {
            throw new ResourceAlreadyExistException(ApiErrorCode.CATEGORY_UNSUBSCRIPTION_ALREADY_EXIST_ERROR);
        }
    }

    public CategoryUnsubscription removeUnsubscriptionByUserId(String userId) {
        Optional<CategoryUnsubscription> unsubscriptionOptional = this.unsubscriptions.stream()
                .filter(us -> Objects.equals(us.getUserId(), userId))
                .findFirst();

        if(unsubscriptionOptional.isPresent()) {

            CategoryUnsubscription unsubscription = unsubscriptionOptional.get();
            this.unsubscriptions.remove(unsubscription);
            unsubscription.associateCategory(null);

            return unsubscription;

        } else {
            throw new ResourceRemoveException(ApiErrorCode.CATEGORY_UNSUBSCRIPTION_NOT_FOUND);
        }
    }

}
