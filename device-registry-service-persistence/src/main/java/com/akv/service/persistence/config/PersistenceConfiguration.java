package com.akv.service.persistence.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EntityScan("com.akv.service.persistence.entity")
@EnableJpaRepositories("com.akv.service.persistence.repository")
public class PersistenceConfiguration {
}
