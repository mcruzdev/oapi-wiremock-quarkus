package com.github.mcruzdev.oapi.extension;

import static io.smallrye.config.common.utils.StringUtil.replaceNonAlphanumericByUnderscores;

public class OpenApiWiremockOperation {

    private final String name;

    public String name() {
        return name;
    }

    private OpenApiWiremockOperation(final String name) {
        String withoutAlphanumeric = replaceNonAlphanumericByUnderscores(name);
        this.name = removeDoubleUnderscores(withoutAlphanumeric).toUpperCase();
    }

    public static OpenApiWiremockOperation create(final String id) {
        return new OpenApiWiremockOperation(id);
    }

    private String removeDoubleUnderscores(final String id) {
        return id.replaceAll("__", "_");
    }
}
