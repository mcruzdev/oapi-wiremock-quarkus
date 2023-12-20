package com.github.mcruzdev.oapi.extension;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiWiremockTest {


    @Test
    @DisplayName("Should throws OpenApiSpecInvalidException when openApiSpec is invalid")
    void generate() {
        // given
        OpenApiWiremock openApiWiremock = OpenApiWiremock.create();

        // when, then
        assertThrows(OpenApiWiremockGeneralException.class, () -> openApiWiremock.generateStubs(""));
    }

    @Test
    @DisplayName("Should return a valid wiremock stub list")
    void should_return_a_valid_wiremock_stub_list() throws IOException {
        // given
        OpenApiWiremock openApiWiremock = OpenApiWiremock.create();

        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("paths/openapi_001.yaml")).getPath();

        String openApiSpec = Files.readString(Path.of(path));

        // when
        List<WiremockStub> stub = openApiWiremock.generateStubs(openApiSpec);

        // then
        assertFalse(stub.isEmpty());
    }

    @Test
    @DisplayName("Should return a valid wiremock stub list with an item with a valid url")
    void should_return_a_valid_wiremock_stub_list_with_an_item_with_a_valid_url() throws IOException {
        // given
        OpenApiWiremock openApiWiremock = OpenApiWiremock.create();

        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("paths/openapi_001.yaml")).getPath();

        String openApiSpec = Files.readString(Path.of(path));

        // when
        List<WiremockStub> stub = openApiWiremock.generateStubs(openApiSpec);

        // then
        assertEquals("/api/v1/users", stub.get(0).getUrl());
    }

    @Test
    @DisplayName("Should return a valid wiremock stub list with an item with a valid url when the path has a parameter")
    void should_return_a_valid_wiremock_stub_list_with_an_item_with_a_valid_url_when_the_path_has_a_parameter() throws IOException {

        // given
        OpenApiWiremock openApiWiremock = OpenApiWiremock.create();

        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("paths/openapi_002.yaml")).getPath();

        String openApiSpec = Files.readString(Path.of(path));

        // when
        List<WiremockStub> stub = openApiWiremock.generateStubs(openApiSpec);

        // then
        assertEquals(1, stub.size());

        assertTrue(stub.get(0).getUrl().startsWith("/api/v1/users/"));
    }


    @Test
    @DisplayName("Should creates two valid wiremock stubs when the path has two operations")
    void should_creates_two_valid_wiremock_stubs_when_the_path_has_two_operations() throws IOException {

        // given
        OpenApiWiremock openApiWiremock = OpenApiWiremock.create();

        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("paths/openapi_003.yaml")).getPath();

        String openApiSpec = Files.readString(Path.of(path));

        // when
        List<WiremockStub> stub = openApiWiremock.generateStubs(openApiSpec);

        // then
        assertEquals(2, stub.size());

        assertTrue(stub.get(0).getUrl().startsWith("/api/v1/users/"));
        assertTrue(stub.get(1).getUrl().startsWith("/api/v1/users/"));
    }

    @Test
    @DisplayName("Should add the operation id name correctly")
    void should_add_the_operation_id_name_correctly() throws IOException {
        OpenApiWiremock openApiWiremock = OpenApiWiremock.create();

        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("paths/openapi_003.yaml")).getPath();

        String openApiSpec = Files.readString(Path.of(path));

        // when
        List<WiremockStub> stub = openApiWiremock.generateStubs(openApiSpec);

        // then
        assertEquals(2, stub.size());

        assertEquals("GETUSERS", stub.get(0).getId());
        assertEquals("UPDATEUSERS", stub.get(1).getId());
    }
}