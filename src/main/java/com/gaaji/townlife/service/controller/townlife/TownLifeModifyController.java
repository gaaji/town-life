package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.service.applicationservice.townlife.TownLifeModifyService;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives")
@RequiredArgsConstructor
public class TownLifeModifyController {

    private final TownLifeModifyService service;

    @PutMapping("/{townLifeId}")
    @ResponseStatus(HttpStatus.OK)
    public TownLifeDetailDto modify(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId,
            @RequestBody TownLifeModifyRequestDto dto
    ) {
        return service.modify(authId, townLifeId, dto);
    }

}
