package com.github.mcruzdev;

import com.github.mcruzdev.oapi.extension.OpenApiWiremock;
import com.github.mcruzdev.oapi.extension.QuteTemplateEngine;
import com.github.mcruzdev.oapi.extension.TemplateEngine;
import com.github.mcruzdev.oapi.extension.WiremockStub;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/specs")
public class GreetingResource {


    @POST
    public String generateWiremock(String openApiSpec) {

        OpenApiWiremock openApiWiremock = OpenApiWiremock.create();

        TemplateEngine templateEngine = new QuteTemplateEngine();

        List<WiremockStub> wiremockStubs = openApiWiremock.generateStubs(openApiSpec);

        return templateEngine.render(wiremockStubs);
    }
}
