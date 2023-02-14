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
@ToString
@SQLDelete(sql = "update comment set deleted_at = current_timestamp where id = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Comment extends BaseEntity {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    private String userId;
    @Embedded
    private CommentContent content;
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "comment")
    private List<CommentLike> likes = new ArrayList<>();

    public abstract TownLife getTownLife();
}
