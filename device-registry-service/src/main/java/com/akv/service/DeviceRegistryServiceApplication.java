package com.akv.service;

import com.akv.service.controller.configuration.WebConfiguration;
import com.akv.service.persistence.config.PersistenceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.akv.service")
@Import({PersistenceConfiguration.class, WebConfiguration.class})
public class DeviceRegistryServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(DeviceRegistryServiceApplication.class, args);
    }

}
