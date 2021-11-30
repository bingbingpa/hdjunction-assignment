package com.hdjunction.global.mockmvc;

import com.hdjunction.global.config.PropertiesConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class BasControllerTest {

    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler documentHandler;
    @Autowired
    private PropertiesConfig propertiesConfig;

    @BeforeEach
    public void setup(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentation) {
        // restDoc snippet 폴더 생성 규칙 변경, request, response 정렬
        documentHandler = document("{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme(propertiesConfig.getProtocolType())
                        .withHost(propertiesConfig.getHost())
                        .withPort(propertiesConfig.getAdminPort())
                )
                // restDoc encoding
                .alwaysDo(documentHandler).addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .build();
    }
}
