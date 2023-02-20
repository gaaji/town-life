package com.gaaji.townlife.service.domain.comment;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@OnDelete(action = OnDeleteAction.CASCADE)
@DiscriminatorValue("child")
public class ChildComment extends Comment {
    @ManyToOne(fetch = FetchType.LAZY)
    private ParentComment parent;

    public static ChildComment create(ParentComment parentComment, CommentContent commentContent, String commenterId) {
        ChildComment childComment = new ChildComment();
        childComment.parent = parentComment;
        childComment.content = commentContent;
        childComment.userId = commenterId;
        return childComment;
    }

    @Override
    public TownLife getTownLife() {
        return parent.getTownLife();
    }
}
