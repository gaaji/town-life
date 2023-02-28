package com.gaaji.townlife.impl;

import com.gaaji.townlife.service.adapter.gaaji.AuthServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.dto.AuthProfileDto;

public class StubAuthServiceClient implements AuthServiceClient {
    @Override
    public AuthProfileDto getAuthProfile(String authId) {
        return new AuthProfileDto(authId, "foo","foo", 36.5);
    }
}
