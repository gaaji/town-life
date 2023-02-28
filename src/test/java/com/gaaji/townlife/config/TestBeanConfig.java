package com.gaaji.townlife.config;

import com.gaaji.townlife.impl.StubAuthServiceClient;
import com.gaaji.townlife.impl.StubTownServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.AuthServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.TownServiceClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBeanConfig {

    @Bean
    public AuthServiceClient authServiceClient() {
        System.out.println("Create Bean AuthServiceClient");
        return new StubAuthServiceClient();
    }

    @Bean
    public TownServiceClient townServiceClient() {
        System.out.println("Create Bean TownServiceClient");
        return new StubTownServiceClient();
    }

}
