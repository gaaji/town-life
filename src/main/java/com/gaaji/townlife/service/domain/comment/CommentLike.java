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
public class CommentLike {
    @Id
    @GenericGenerator()
    @GeneratedValue
    private String id;
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
}
