package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.service.domain.BaseEntity;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.comment.ParentComment;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@SQLDelete(sql = "update town_life set deleted_at = current_timestamp where id = ?")
public abstract class TownLife extends BaseEntity {
    @Id
    @GenericGenerator()
    @GeneratedValue
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    private String authorId;
    @OneToMany(mappedBy = "townLife")
    private List<ParentComment> comments;
    @Embedded
    private TownLifeContent content;
    private LocalDateTime deletedAt;
    private String townId;
    @OneToMany(mappedBy = "townLife")
    private List<TownLifeSubscription> subscriptions;
}
