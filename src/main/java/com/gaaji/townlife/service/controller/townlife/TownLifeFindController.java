package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.global.utils.validation.ValidateResourceAccess;
import com.gaaji.townlife.service.applicationservice.townlife.TownLifeFindService;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives")
@RequiredArgsConstructor
public class TownLifeFindController {

    private final TownLifeFindService townLifeFindService;

    @GetMapping("/{townLifeId}")
    @ResponseStatus(HttpStatus.OK)
    public TownLifeDetailDto visit(
            @RequestHeader("X-TOWN-TOKEN") String townToken,
            @PathVariable String townLifeId
    ) {
        ValidateResourceAccess.validateAuthenticatedTownToken(townToken);
        return townLifeFindService.visit(townLifeId);
    }
}
