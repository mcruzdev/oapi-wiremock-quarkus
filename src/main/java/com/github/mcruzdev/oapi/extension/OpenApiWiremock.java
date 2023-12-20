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

        Set<Map.Entry<String, PathItem>> entries = openAPI.getPaths().entrySet();

        List<WiremockStub> wiremockStubs = new ArrayList<>();

        for (Map.Entry<String, PathItem> entry : entries) {
            wiremockStubs = generateWiremockStubs(entry);
        }

        return wiremockStubs;
    }

    private static List<WiremockStub> generateWiremockStubs(Map.Entry<String, PathItem> entries) {
        List<WiremockStub> wiremockStubs = new ArrayList<>();
        String pathName = entries.getKey();
        PathItem pathItem = entries.getValue();

        for (Map.Entry<PathItem.HttpMethod, Operation> entry : pathItem.readOperationsMap().entrySet()) {
            PathItem.HttpMethod httpMethod = entry.getKey();
            Operation operation = entry.getValue();

            WiremockStub wiremockStub = new WiremockStub.Builder()
                    .id(operation.getOperationId())
                    .url(OpenApiWiremockUrl.create(pathName, operation))
                    .method(httpMethod.name())
                    .build();

            wiremockStubs.add(wiremockStub);
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
