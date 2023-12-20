package com.github.mcruzdev.oapi.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenApiWiremockOptions {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiWiremockOperation.class);


    private OpenApiWiremockOptions(final Boolean withDefaults) {
        if (withDefaults) {
            LOGGER.debug("with defaults");
        }
    }

    public static OpenApiWiremockOptions withDefaults() {
        return new OpenApiWiremockOptions(true);
    }

}
