package com.gaaji.townlife.service.domain.comment;

import com.gaaji.townlife.service.domain.BaseEntity;
import com.gaaji.townlife.service.domain.townlife.TownLife;
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
@SQLDelete(sql = "update comment set deleted_at = current_timestamp where id = ?")
public abstract class Comment extends BaseEntity {
    @Id
    @GenericGenerator()
    @GeneratedValue
    private String id;
    private String userId;
    @Embedded
    private CommentContent content;
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "comment")
    private List<CommentLike> likes;

    public abstract TownLife getTownLife();
}
