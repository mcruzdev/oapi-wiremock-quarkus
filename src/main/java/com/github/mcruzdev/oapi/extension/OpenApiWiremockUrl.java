package com.github.mcruzdev.oapi.extension;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OpenApiWiremockUrl {

    private static final String PATH = "path";
    private static final String OPEN_BRACE = "{";
    private static final String CLOSE_BRACE = "}";

    static String create(final String url, final Operation operation) {

        String finalUrl = "";

        List<Parameter> parameters = operation.getParameters();

        if (parameters != null) {
            for (Parameter parameter : parameters) {
                if (parameter.getIn().equals(PATH)) {
                    finalUrl = url.replace(OPEN_BRACE + parameter.getName() + CLOSE_BRACE, exampleOrElse(parameter));
                }
            }
        }

        return finalUrl;
    }

    /**
     * If the parameter has an example, return it, otherwise return a random UUID as example value.
     *
     * @param parameter the parameter to get the example from.
     * @return the example value or a random UUID.
     */
    private static String exampleOrElse(final Parameter parameter) {
        final String id = UUID.randomUUID().toString();
        return Optional.ofNullable(parameter.getExample()).orElse(id).toString();
    }

}
