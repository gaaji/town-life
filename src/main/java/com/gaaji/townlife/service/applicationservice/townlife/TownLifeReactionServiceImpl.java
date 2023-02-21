package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceSaveException;
import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoRequestDto;
import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoResponseDto;
import com.gaaji.townlife.service.domain.reaction.PostReaction;
import com.gaaji.townlife.service.domain.reaction.QuestionReaction;
import com.gaaji.townlife.service.domain.townlife.PostTownLife;
import com.gaaji.townlife.service.domain.townlife.QuestionTownLife;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.event.nonkafka.dto.TownLifeEventBody;
import com.gaaji.townlife.service.event.nonkafka.townlife.TownLifeReactionAddedEvent;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TownLifeReactionServiceImpl implements TownLifeReactionService {

    private final TownLifeRepository townLifeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ReactionDoResponseDto doReaction(String userId, String townLifeId, ReactionDoRequestDto dto) {
        TownLife townLife = getTownLifeById(townLifeId);

        ReactionDoResponseDto responseDto = null;
        if (townLife instanceof PostTownLife) {
            PostReaction reaction = townLife.addReaction(PostReaction.of(userId, dto.getEmoji()));
            responseDto = ReactionDoResponseDto.of(townLife.getId(), reaction.getUserId(), reaction.getEmoji());

        } else if (townLife instanceof QuestionTownLife){
            QuestionReaction reaction = townLife.addReaction(QuestionReaction.of(userId));
            responseDto = ReactionDoResponseDto.of(townLife.getId(), reaction.getUserId());
        }
        if (responseDto == null) throw new ResourceSaveException(ApiErrorCode.REACTION_SAVE_ERROR);

        eventPublisher.publishEvent(new TownLifeReactionAddedEvent(
                TownLifeReactionService.class,
                TownLifeEventBody.of(responseDto.getUserId(), townLife.getSubscriptions())
        ));

        return responseDto;
    }

    @Override
    @Transactional
    public void cancelReaction(String userId, String townLifeId) {
        TownLife townLife = getTownLifeById(townLifeId);

        townLife.removeReactionByUserId(userId);
    }

    private TownLife getTownLifeById(String id) {
        return townLifeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }

}
