package com.gaaji.townlife.impl;

import com.gaaji.townlife.service.adapter.gaaji.TownServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.dto.TownAddressDto;

public class StubTownServiceClient implements TownServiceClient {
    @Override
    public TownAddressDto getTownAddress(String townId) {
        return new TownAddressDto("테스트동");
    }
}
