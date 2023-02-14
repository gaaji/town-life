package com.gaaji.townlife.service.domain.comment;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@OnDelete(action = OnDeleteAction.CASCADE)
public class ParentComment extends Comment {
    @ManyToOne(fetch = FetchType.LAZY)
    private TownLife townLife;
    @OneToMany(mappedBy = "parent")
    private List<ChildComment> children;
}
