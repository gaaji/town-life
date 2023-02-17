package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.service.domain.BaseEntity;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @ToString(exclude = { "category", "townLifeCounter", "subscriptions", "comments", "attachedImages" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update town_life set deleted_at = current_timestamp where id = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(indexes = {
        @Index(name = "idx__town_life__author_id__id_desc", columnList = "authorId, id DESC"),
        @Index(name = "idx__town_life__town_id__id_desc", columnList = "townId, id DESC"),
})
public abstract class TownLife extends BaseEntity {

    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    protected String id;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Category category;
    protected String authorId;
    protected String townId;
    protected LocalDateTime deletedAt;
    @Embedded
    protected TownLifeContent content;
    @OneToMany(mappedBy = "townLife")
    protected List<ParentComment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "townLife")
    protected List<TownLifeSubscription> subscriptions = new ArrayList<>();
    @OneToMany(mappedBy = "townLife", cascade = CascadeType.PERSIST, orphanRemoval = true)
    protected List<AttachedImage> attachedImages = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    protected TownLifeCounter townLifeCounter;

    protected TownLife(String authorId, String townId, String title, String text, String location) {
        this.authorId = authorId;
        this.townId = townId;
        this.content = TownLifeContent.of(title, text, location);
    }

    public void updateContent(String title, String text, String location) {
        this.content = TownLifeContent.of(title, text, location);
    }

    public TownLife associateCategory(Category category) {
        this.category = category.addTownLife(this);;
        return this;
    }

    public void addSubscription(TownLifeSubscription townLifeSubscription) {
        this.subscriptions.add(townLifeSubscription);
    }

    public void associateCounter(TownLifeCounter townLifeCounter) {
        this.townLifeCounter = townLifeCounter;
        this.townLifeCounter.associateTownLife(this);
    }

    public String getThumbnailSrc() {
        if(this.attachedImages.size() == 0) return null;
        return this.attachedImages.get(0).getSrc();
    }

}
