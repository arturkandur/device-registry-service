package com.akv.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.akv.service")
@EnableJpaRepositories(basePackages = "com.akv.service.persistence.repository")
@EntityScan(basePackages = "com.akv.service.persistence.entity")
public class DeviceRegistryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceRegistryServiceApplication.class, args);
    }

}
