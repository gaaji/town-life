package com.gaaji.townlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
@EnableFeignClients
@ConfigurationPropertiesScan
@EnableAsync
public class TownLifeApplication {
	public static void main(String[] args) {
		SpringApplication.run(TownLifeApplication.class, args);
	}

}
