package com.gaaji.townlife.service.domain.comment;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(
        indexes = {
                @Index(name = "idx__comment_like__user_id", columnList = "user_id"),
        },
        uniqueConstraints = {@UniqueConstraint(name = "comment_like__unique__user_id__comment_id", columnNames = {"user_id", "comment_id"})}
)
public class CommentLike {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ulid.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static CommentLike create(String userId) {
        CommentLike e = new CommentLike();
        e.userId = userId;
        return e;
    }

    public void associate(Comment comment) {
        this.comment = comment;
        comment.addLike(this);
    }

    public void unAssociate(Comment comment) {
        this.comment = null;
        comment.removeLike(this);
    }
}
