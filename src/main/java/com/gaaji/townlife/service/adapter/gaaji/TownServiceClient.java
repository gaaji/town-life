package com.gaaji.townlife.service.adapter.gaaji;

import com.gaaji.townlife.service.adapter.gaaji.dto.TownAddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "town-service", primary = false)
public interface TownServiceClient {

    @GetMapping("/town/{townId}")
    TownAddressDto getTownAddress(@PathVariable("townId") String townId);

}
