package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.service.applicationservice.townlife.TownLifeRemoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives")
@RequiredArgsConstructor
public class TownLifeRemoveController {

    private final TownLifeRemoveService service;

    @DeleteMapping("/{townLifeId}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId
    ) {
        service.remove(authId, townLifeId);
    }

}
