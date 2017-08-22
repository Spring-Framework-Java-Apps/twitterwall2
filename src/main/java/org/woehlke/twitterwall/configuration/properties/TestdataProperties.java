package org.woehlke.twitterwall.configuration.properties;


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

    @Validated
    public static class Oodm {

        @Valid
        private Entities entities = new Entities();

        @Validated
        public static class Entities {

            @Valid
            private Url url = new Url();

            @Valid
            private Tweet tweet = new Tweet();

            @Valid
            private User user = new User();

            @Validated
            public static class Url {

                @NotNull
                private List<String> url = new ArrayList<>();

                public List<String> getUrl() {
                    return url;
                }

                public void setUrl(List<String> url) {
                    this.url = url;
                }
            }

            @Validated
            public static class User {

                @NotNull
                private List<Long> idTwitter = new ArrayList<>();

                @NotNull
                private List<String> screenName = new ArrayList<>();

                @NotNull
                private List<String> descriptions = new ArrayList<>();

                public List<Long> getIdTwitter() {
                    return idTwitter;
                }

                public void setIdTwitter(List<Long> idTwitter) {
                    this.idTwitter = idTwitter;
                }

                public List<String> getScreenName() {
                    return screenName;
                }

                public void setScreenName(List<String> screenName) {
                    this.screenName = screenName;
                }

                public List<String> getDescriptions() {
                    return descriptions;
                }

                public void setDescriptions(List<String> descriptions) {
                    this.descriptions = descriptions;
                }
            }

            @Validated
            public static class Tweet {

                @NotNull
                private List<Long> idTwitter = new ArrayList<>();

                public List<Long> getIdTwitter() {
                    return idTwitter;
                }

                public void setIdTwitter(List<Long> idTwitter) {
                    this.idTwitter = idTwitter;
                }
            }

            public Url getUrl() {
                return url;
            }

            public void setUrl(Url url) {
                this.url = url;
            }

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public Tweet getTweet() {
                return tweet;
            }

            public void setTweet(Tweet tweet) {
                this.tweet = tweet;
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
