package com.gaaji.townlife.service.domain.townlife;

import com.gaaji.townlife.service.domain.reaction.Reaction;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@ToString
@DiscriminatorValue("question")
public class QuestionTownLife extends TownLife {
    @OneToMany(mappedBy = "townLife")
    private List<Reaction> reactions = new ArrayList<>();
}
