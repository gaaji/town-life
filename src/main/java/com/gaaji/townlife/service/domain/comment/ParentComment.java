package com.gaaji.townlife.service.domain.comment;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@OnDelete(action = OnDeleteAction.CASCADE)
@DiscriminatorValue("parent")
public class ParentComment extends Comment {
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
    @OneToMany(mappedBy = "parent")
    private List<ChildComment> children;

    public ParentComment(TownLife townLife, CommentContent content, String userId) {
        this.townLife = townLife;
        this.content = content;
        this.userId = userId;
    }


    public static ParentComment create(TownLife townLife, CommentContent commentContent, String authId) {
        ParentComment parentComment = new ParentComment();
        parentComment.townLife = townLife;
        parentComment.content = commentContent;
        parentComment.userId = authId;
        return parentComment;
    }
}
