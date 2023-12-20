import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

public class OpenApiWiremockStubs {

    public static final String GET_USERS_URL = "/api/v1/users";
    public static final MappingBuilder MAPPING_BUILDER_GET_USERS = WireMock.get(WireMock.urlEqualTo(GET_USERS_URL))
            .willReturn(WireMock.aResponse());


}