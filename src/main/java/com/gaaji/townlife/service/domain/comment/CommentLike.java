package com.gaaji.townlife.service.domain.comment;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@Table(indexes = {
        @Index(name = "idx__comment_like__user_id", columnList = "userId"),
})
public class CommentLike {
    @Id
    @GenericGenerator(name = "ulidGenerator", strategy = "com.gaaji.townlife.global.utils.ULIDGenerator")
    @GeneratedValue(generator = "ulidGenerator")
    private String id;
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
}
