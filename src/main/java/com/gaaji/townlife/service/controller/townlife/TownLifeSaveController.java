package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.global.utils.validation.ValidateResourceAccess;
import com.gaaji.townlife.service.applicationservice.townlife.TownLifeSaveService;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/town-lives")
@RequiredArgsConstructor
public class TownLifeSaveController {

    private final TownLifeSaveService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TownLifeDetailDto save(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestHeader("X-TOWN-TOKEN") String townToken,
            @RequestBody TownLifeSaveRequestDto dto
    ){
        String townId = ValidateResourceAccess.validateAuthenticatedTownToken(townToken);
        return service.save(authId, townId, dto);
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TownLifeDetailDto> test_save_20_townLife(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestHeader("X-TOWN-TOKEN") String townToken,
            @RequestBody TownLifeSaveRequestDto dto
    ) {
        String townId = ValidateResourceAccess.validateAuthenticatedTownToken(townToken);
        List<TownLifeDetailDto> a = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            dto.setTitle("테스트" + i);
            a.add(service.save(authId, townId, dto));
        }
        return a;
    }

}
