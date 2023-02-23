package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoRequestDto;
import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoResponseDto;

public interface TownLifeReactionService {

    ReactionDoResponseDto doReaction(String userId, String townLifeId, ReactionDoRequestDto dto);
    void cancelReaction(String userId, String townLifeId);

}
