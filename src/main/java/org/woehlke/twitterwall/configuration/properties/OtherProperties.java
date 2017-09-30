package org.woehlke.twitterwall.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@Validated
@ConfigurationProperties
public class OtherProperties {

    @Valid
    private Server server = new Server();

    @Validated
    public static class Server {

        @Valid
        private Error error = new Error();

        @Validated
        public static class Error {

            @NotNull
            private String path;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

        }

        public Error getError() {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
