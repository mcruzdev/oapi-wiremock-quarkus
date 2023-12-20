package com.github.mcruzdev.oapi.extension;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import static io.smallrye.config.common.utils.StringUtil.replaceNonAlphanumericByUnderscores;

public class OpenApiWiremockOperation {


    private static final String UNDERSCORE = "_";
    private static final String DOUBLE_UNDERSCORE = "__";
    private final String name;

    public String name() {
        return name;
    }

    private OpenApiWiremockOperation(final String name) {
        String withoutAlphanumeric = replaceNonAlphanumericByUnderscores(name);
        this.name = removeDoubleUnderscores(withoutAlphanumeric).toUpperCase(Locale.ROOT);

    }

    public static OpenApiWiremockOperation create(final String id) {
        return new OpenApiWiremockOperation(id);
    }

    private String removeDoubleUnderscores(final String id) {
        return id.replaceAll(DOUBLE_UNDERSCORE, UNDERSCORE);
    }
}
