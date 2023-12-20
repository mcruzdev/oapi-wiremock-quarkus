package com.github.mcruzdev.oapi.extension;

import io.quarkus.qute.Engine;
import io.quarkus.qute.ReflectionValueResolver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class QuteTemplateEngine implements TemplateEngine {

    private final Engine engine;

    public QuteTemplateEngine() {
        this.engine = Engine.builder()
                .addDefaults()
                .addValueResolver(new ReflectionValueResolver())
                .removeStandaloneLines(true)
                .strictRendering(false)
                .build();

        tryLoadTemplates();
    }

    private void tryLoadTemplates() {
        try {
            String path = Objects.requireNonNull(this.getClass()
                            .getClassLoader()
                            .getResource("templates/wiremock-stub.qute"))
                    .getPath();

            String content = Files.readString(Path.of(path));

            this.engine.putTemplate("wiremock-stub.qute",
                    this.engine.parse(content));

        } catch (Exception e) {
            throw new OpenApiWiremockGeneralException("Could not load template");
        }
    }


    @Override
    public String render(final List<WiremockStub> wiremockStubs) {
        return this.engine.getTemplate("wiremock-stub.qute").data("wiremockStubs", wiremockStubs).render();
    }
}
