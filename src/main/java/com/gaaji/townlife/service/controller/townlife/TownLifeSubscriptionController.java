package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.service.applicationservice.townlife.TownLifeSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives/{townLifeId}/subscriptions")
@RequiredArgsConstructor
public class TownLifeSubscriptionController {

    private final TownLifeSubscriptionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId
    ) {
       service.subscribe(authId, townLifeId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId
    ) {
        service.unsubscribe(authId, townLifeId);
    }

}
