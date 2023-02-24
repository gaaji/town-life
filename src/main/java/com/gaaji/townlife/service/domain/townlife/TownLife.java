package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceAlreadyExistException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.service.domain.BaseEntity;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import com.gaaji.townlife.service.domain.reaction.Reaction;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
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
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
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
    @OneToMany(mappedBy = "townLife", orphanRemoval = true)
    protected List<TownLifeSubscription> subscriptions = new ArrayList<>();
    @OneToMany(mappedBy = "townLife", cascade = CascadeType.ALL, orphanRemoval = true)
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
        validateExistsSubscriptionByUserId(townLifeSubscription.getUserId());

        this.subscriptions.add(townLifeSubscription);
        townLifeSubscription.associateTownLife(this);
    }

    private void validateExistsSubscriptionByUserId(String userId) {
        Optional<TownLifeSubscription> subscriptionOptional = this.subscriptions.stream()
                .filter(s -> Objects.equals(s.getUserId(), userId))
                .findFirst();

        if (subscriptionOptional.isPresent()) {
            throw new ResourceAlreadyExistException(ApiErrorCode.TOWN_LIFE_SUBSCRIPTION_ALREADY_EXIST_ERROR);
        }
    }

    public void removeSubscriptionByUserId(String userId) {
        Optional<TownLifeSubscription> subscriptionOpt = this.subscriptions.stream()
                .filter(s -> Objects.equals(s.getUserId(), userId))
                .findFirst();

        if (subscriptionOpt.isPresent()) {

            TownLifeSubscription subscription = subscriptionOpt.get();
            this.subscriptions.remove(subscription);
            subscription.associateTownLife(null);

        } else {
            throw new ResourceRemoveException(ApiErrorCode.TOWN_LIFE_SUBSCRIPTION_NOT_FOUND);
        }
    }

    public void associateCounter(TownLifeCounter townLifeCounter) {
        this.townLifeCounter = townLifeCounter;
    }

    public String getThumbnailSrc() {
        if(this.attachedImages.size() == 0) return null;
        return this.attachedImages.get(0).getSrc();
    }

    public int getCommentCount() { return townLifeCounter.getCommentCount(); }
    public int getInterestCount() { return townLifeCounter.getInterestCount(); }
    public int getReactionCount() { return townLifeCounter.getReactionCount(); }
    public int getViewCount() { return townLifeCounter.getViewCount(); }

    public abstract <T extends Reaction> T addReaction(T reaction);
    public abstract void removeReactionByUserId(String userId);

    public void addAttachedImage(AttachedImage attachedImage) {
        this.attachedImages.add(attachedImage);
        attachedImage.associateTownLife(this);
    }

    public String[] removeAllAttachedImages() {
        if(this.attachedImages.size()==0)
            throw new ResourceRemoveException(ApiErrorCode.IMAGE_NOT_FOUND);

        String[] srcs = this.attachedImages.stream()
                .map(AttachedImage::getSrc)
                .toArray(String[]::new);

        this.attachedImages.clear();

        return srcs;
    }

    public void addComment(ParentComment parentComment) {
        this.comments.add(parentComment);
    }

    public List<TownLifeSubscription> getAuthorAmongSubscription() {
        return this.subscriptions.stream()
                .filter(subscriptions -> Objects.equals(this.authorId, subscriptions.getUserId()))
                .collect(Collectors.toList());
    }
}
