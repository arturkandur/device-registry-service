package com.akv.service.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
abstract class WebControllerTest {

    MockMvc mockMvc;

    protected MockMvc buildMockMvc(RestDocumentationContextProvider restDocumentation, Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

}
