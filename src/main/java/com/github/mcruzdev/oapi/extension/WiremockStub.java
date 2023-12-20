package com.github.mcruzdev.oapi.extension;

public class WiremockStub {

    private String url;
    private String id;

    public String getUrl() {
        return this.url;
    }

    public String getId() {
        return this.id;
    }

    public static class Builder {
        private String url;
        private String id;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder id(String id) {
            this.id = OpenApiWiremockOperation.create(id).name();
            return this;
        }

        public WiremockStub build() {
            WiremockStub stub = new WiremockStub();
            stub.url = this.url;
            stub.id = this.id;
            return stub;
        }
    }
}
