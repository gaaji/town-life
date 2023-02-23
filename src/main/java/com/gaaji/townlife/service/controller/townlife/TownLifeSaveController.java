package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.global.utils.validation.ValidateResourceAccess;
import com.gaaji.townlife.service.applicationservice.townlife.TownLifeSaveService;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives")
@RequiredArgsConstructor
public class TownLifeSaveController {

    private final TownLifeSaveService townLifeSaveService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TownLifeDetailDto save(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestHeader("X-TOWN-TOKEN") String townToken,
            @RequestBody TownLifeSaveRequestDto dto
    ){
        String townId = ValidateResourceAccess.validateAuthenticatedTownToken(townToken);
        return townLifeSaveService.save(authId, townId, dto);
    }

}
