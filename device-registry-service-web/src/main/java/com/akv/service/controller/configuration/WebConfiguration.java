package com.akv.service.controller.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import(SwaggerConfiguration.class)
public class WebConfiguration {
}
