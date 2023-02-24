package com.gaaji.townlife.service.controller.category;

import com.gaaji.townlife.service.applicationservice.category.CategoryFindService;
import com.gaaji.townlife.service.controller.category.dto.CategoryListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryFindController {

    private final CategoryFindService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryListDto> findList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId) {
        return service.findList(authId);
    }

}
