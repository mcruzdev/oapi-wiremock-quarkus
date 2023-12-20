package com.github.mcruzdev.oapi.extension;

import java.util.List;

public interface TemplateEngine {


    String render(List<WiremockStub> wiremockStubs);
}
