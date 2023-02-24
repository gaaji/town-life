package com.gaaji.townlife.service.controller.townlife;

import com.gaaji.townlife.service.applicationservice.townlife.TownLifeImageService;
import com.gaaji.townlife.service.controller.townlife.dto.AttachedImageDto;
import com.gaaji.townlife.service.controller.townlife.dto.ImageOrderIndexRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/town-lives/{townLifeId}/images")
@RequiredArgsConstructor
public class TownLifeImageController {

    private final TownLifeImageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<AttachedImageDto> upload(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId,
            @RequestPart("orderIndex") ImageOrderIndexRequestDto dto,
            @RequestPart("image") MultipartFile[] multipartFiles
    ) {
        return service.upload(authId, townLifeId, dto.getOrderIndexes(), multipartFiles);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AttachedImageDto> update(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId,
            @RequestPart("orderIndex") ImageOrderIndexRequestDto dto,
            @RequestPart("image") MultipartFile[] multipartFiles
    ) {
        return service.update(authId, townLifeId, dto.getOrderIndexes(), multipartFiles);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AttachedImageDto> deleteAll(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String townLifeId
    ) {
        return service.deleteAll(authId, townLifeId);
    }

}
