package org.woehlke.twitterwall.conf.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
@Validated
@ConfigurationProperties(prefix="twitterwall.testdata")
public class TestdataProperties {

    @Valid
    private Oodm oodm = new Oodm();

    private static class Oodm {

        @Valid
        private Entities entities = new Entities();

        public static class Entities {

            @Valid
            private User user = new User();

            public static class User {

                @NotNull
                private List<String> descriptions = new ArrayList<>();

                public List<String> getDescriptions() {
                    return descriptions;
                }

                public void setDescriptions(List<String> descriptions) {
                    this.descriptions = descriptions;
                }
            }

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }
        }

        public Entities getEntities() {
            return entities;
        }

        public void setEntities(Entities entities) {
            this.entities = entities;
        }
    }

    public Oodm getOodm() {
        return oodm;
    }

    public void setOodm(Oodm oodm) {
        this.oodm = oodm;
    }
}
