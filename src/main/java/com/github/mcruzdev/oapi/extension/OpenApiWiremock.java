package com.github.mcruzdev.oapi.extension;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import java.util.*;

public class OpenApiWiremock {

    private final OpenApiWiremockOptions options;

    private OpenApiWiremock(final OpenApiWiremockOptions options) {
        this.options = options;
    }

    public static OpenApiWiremock create(final OpenApiWiremockOptions options) {
        return new OpenApiWiremock(options);
    }

    public static OpenApiWiremock create() {
        return new OpenApiWiremock(OpenApiWiremockOptions.withDefaults());
    }

    public List<WiremockStub> generateStubs(final String openApiSpec) {

        Optional<OpenAPI> openApi = getOpenApi(openApiSpec);

        OpenAPI openAPI = openApi.orElseThrow(() -> new OpenApiWiremockGeneralException("OpenAPI spec is invalid"));

        List<WiremockStub> wiremockStubs = new ArrayList<>();

        Set<Map.Entry<String, PathItem>> entries = openAPI.getPaths().entrySet();

        for (Map.Entry<String, PathItem> entry : entries) {
            for (Operation operation : entry.getValue().readOperations()) {
                wiremockStubs.add(new WiremockStub.Builder()
                        .id(operation.getOperationId())
                        .url(OpenApiWiremockUrl.create(entry.getKey(), operation))
                        .build());
            }
        }

        return wiremockStubs;
    }

    private Optional<OpenAPI> getOpenApi(final String openApiSpec) {
        OpenAPIV3Parser parser = new OpenAPIV3Parser();
        SwaggerParseResult parseResult = parser.readContents(openApiSpec);
        if (!parseResult.getMessages().isEmpty()) {
            throw new OpenApiWiremockGeneralException(parseResult.getMessages().toString());
        }
        return Optional.of(parseResult.getOpenAPI());
    }

}
