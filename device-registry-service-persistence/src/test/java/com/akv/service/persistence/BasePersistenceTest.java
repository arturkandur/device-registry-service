package com.akv.service.persistence;

import com.akv.service.persistence.config.PersistenceConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = BasePersistenceTest.TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(TestcontainersConfiguration.class)
public abstract class BasePersistenceTest {

    @Configuration
    @EnableAutoConfiguration
    @Import(PersistenceConfiguration.class)
    static class TestConfig {}

}
