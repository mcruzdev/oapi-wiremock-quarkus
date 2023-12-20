package com.github.mcruzdev.oapi.extension;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuteTemplateEngineTest {


    @Test
    @DisplayName("Should render the template with the stubs")
    void should_render_the_template_with_the_stubs() {

        // given
        TemplateEngine templateEngine = new QuteTemplateEngine();

        // when
        String stubClass = templateEngine.render(
                List.of(
                        new WiremockStub.Builder()
                                .id("GET_USERS")
                                .url("/api/v1/users")
                                .build()
                )
        );

        System.out.println(stubClass);

    }

}