package com.gaaji.townlife.service.controller.category;

import com.gaaji.townlife.service.applicationservice.category.CategorySubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories/{categoryId}/subscriptions")
@RequiredArgsConstructor
public class CategorySubscriptionController {

    private final CategorySubscriptionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String categoryId
    ) {
       service.subscribe(authId, categoryId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String categoryId
    ) {
        service.unsubscribe(authId, categoryId);
    }

}
