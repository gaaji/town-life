package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.global.utils.validation.ValidateResourceAccess;
import com.gaaji.townlife.service.applicationservice.townlife.TownLifeFindService;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/town-lives")
@RequiredArgsConstructor
public class TownLifeFindController {

    private final TownLifeFindService service;

    @GetMapping("/only/{townLifeId}")
    @ResponseStatus(HttpStatus.OK)
    public TownLifeDetailDto findById(@PathVariable String townLifeId) {
        return service.findById(townLifeId);
    }

    @GetMapping("/{townLifeId}")
    @ResponseStatus(HttpStatus.OK)
    public TownLifeDetailDto visit(
            @RequestHeader("X-TOWN-TOKEN") String townToken,
            @PathVariable String townLifeId
    ) {
        ValidateResourceAccess.validateAuthenticatedTownToken(townToken);
        return service.visit(townLifeId);
    }

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    public TownLifeListResponseDto findListByTownId(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestHeader("X-TOWN-TOKEN") String townToken,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime requestTime,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        String townId = ValidateResourceAccess.validateAuthenticatedTownToken(townToken);
        return service.findListByTownId(authId, townId, requestTime, page, size);
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public TownLifeListResponseDto findListByUserId(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime requestTime,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return service.findListByUserId(authId, requestTime, page, size);
    }

}
