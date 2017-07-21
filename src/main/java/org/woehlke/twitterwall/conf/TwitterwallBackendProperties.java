package org.woehlke.twitterwall.conf;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Component
@Validated
@ConfigurationProperties(prefix="twitterwall.backend")
public class TwitterwallBackendProperties {

    @Valid
    public Twitter twitter = new Twitter();

    @Valid
    public Url url = new Url();

    public static class Twitter {

        //@NotNull
        private String accessToken;

        //@NotNull
        private String accessTokenSecret;

        //@NotNull
        private String consumerKey;

        //@NotNull
        private String consumerSecret;

        @NotNull
        private Integer millisToWaitBetweenTwoApiCalls;

        @Valid
        private Integer millisToWaitForFetchTweetsFromTwitterSearch;

        public Integer getMillisToWaitBetweenTwoApiCalls() {
            return millisToWaitBetweenTwoApiCalls;
        }

        public void setMillisToWaitBetweenTwoApiCalls(Integer millisToWaitBetweenTwoApiCalls) {
            this.millisToWaitBetweenTwoApiCalls = millisToWaitBetweenTwoApiCalls;
        }

        public Integer getMillisToWaitForFetchTweetsFromTwitterSearch() {
            return millisToWaitForFetchTweetsFromTwitterSearch;
        }

        public void setMillisToWaitForFetchTweetsFromTwitterSearch(Integer millisToWaitForFetchTweetsFromTwitterSearch) {
            this.millisToWaitForFetchTweetsFromTwitterSearch = millisToWaitForFetchTweetsFromTwitterSearch;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessTokenSecret() {
            return accessTokenSecret;
        }

        public void setAccessTokenSecret(String accessTokenSecret) {
            this.accessTokenSecret = accessTokenSecret;
        }

        public String getConsumerKey() {
            return consumerKey;
        }

        public void setConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
        }

        public String getConsumerSecret() {
            return consumerSecret;
        }

        public void setConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
        }
    }

    public static class Url {

        @NotNull
        private Long connTimeToLive;

        @NotNull
        private Long maxIdleTime;

        @NotNull
        private Boolean fetchTestDataVerbose;

        public Long getConnTimeToLive() {
            return connTimeToLive;
        }

        public void setConnTimeToLive(Long connTimeToLive) {
            this.connTimeToLive = connTimeToLive;
        }

        public Long getMaxIdleTime() {
            return maxIdleTime;
        }

        public void setMaxIdleTime(Long maxIdleTime) {
            this.maxIdleTime = maxIdleTime;
        }

        public Boolean getFetchTestDataVerbose() {
            return fetchTestDataVerbose;
        }

        public void setFetchTestDataVerbose(Boolean fetchTestDataVerbose) {
            this.fetchTestDataVerbose = fetchTestDataVerbose;
        }
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}