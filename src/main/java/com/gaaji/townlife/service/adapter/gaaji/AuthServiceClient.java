package com.gaaji.townlife.service.adapter.gaaji;

import com.gaaji.townlife.service.adapter.gaaji.dto.AuthProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "auth-service", primary = false)
public interface AuthServiceClient {

    @GetMapping("/auth/{authId}")
    AuthProfileDto getAuthProfile(@PathVariable String authId);

}
