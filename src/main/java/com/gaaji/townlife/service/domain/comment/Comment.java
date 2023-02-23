package com.gaaji.townlife.service.domain.comment;

import com.gaaji.townlife.service.domain.BaseEntity;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update comment set deleted_at = current_timestamp where id = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(indexes = {
        @Index(name = "idx__comment__user_id", columnList = "userId"),
})
public abstract class Comment extends BaseEntity {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    protected String userId;
    @Embedded
    protected CommentContent content;
    protected LocalDateTime deletedAt;
    @OneToMany(mappedBy = "comment")
    protected List<CommentLike> likes = new ArrayList<>();

    public abstract TownLife getTownLife();

    public void addLike(CommentLike commentLike) {
        this.likes.add(commentLike);
    }

    public void removeLike(CommentLike like) {
        this.likes.remove(like);
    }

    public void modify(CommentContent content) {
        if(content.getText().equals(this.content.getText()))
            if(content.getLocation().equals(this.content.getLocation()))
                return;
        this.content = content;
    }
}
