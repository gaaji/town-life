package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.service.applicationservice.townlife.TownLifeReactionService;
import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoRequestDto;
import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives/{townLifeId}/reactions")
@RequiredArgsConstructor
public class TownLifeReactionController {

    private final TownLifeReactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ReactionDoResponseDto doReaction(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId,
            @RequestBody ReactionDoRequestDto dto
    ) {
        return service.doReaction(authId, townLifeId, dto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void cancelReaction(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId
    ) {
        service.cancelReaction(authId, townLifeId);
    }

}
