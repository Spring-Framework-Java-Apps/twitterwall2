package org.woehlke.twitterwall;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
@Validated
@ConfigurationProperties(prefix="twitterwall.scheduler")
public class TwitterwallSchedulerProperties {

    @NotNull
    private Boolean allowFetchTweetsFromTwitterSearch;

    @NotNull
    private Boolean allowUpdateTweets;

    @NotNull
    private Boolean allowUpdateUserProfiles;

    @NotNull
    private Boolean allowUpdateUserProfilesFromMention;

    @NotNull
    private String herokuDbRowsLimit;

    @NotNull
    private Boolean skipFortesting;

    @Valid
    private FetchUserList fetchUserList = new FetchUserList();

    @Valid
    private Facade facade = new Facade();

    public Boolean getAllowFetchTweetsFromTwitterSearch() {
        return allowFetchTweetsFromTwitterSearch;
    }

    public void setAllowFetchTweetsFromTwitterSearch(Boolean allowFetchTweetsFromTwitterSearch) {
        this.allowFetchTweetsFromTwitterSearch = allowFetchTweetsFromTwitterSearch;
    }

    public Boolean getAllowUpdateTweets() {
        return allowUpdateTweets;
    }

    public void setAllowUpdateTweets(Boolean allowUpdateTweets) {
        this.allowUpdateTweets = allowUpdateTweets;
    }

    public Boolean getAllowUpdateUserProfiles() {
        return allowUpdateUserProfiles;
    }

    public void setAllowUpdateUserProfiles(Boolean allowUpdateUserProfiles) {
        this.allowUpdateUserProfiles = allowUpdateUserProfiles;
    }

    public Boolean getAllowUpdateUserProfilesFromMention() {
        return allowUpdateUserProfilesFromMention;
    }

    public void setAllowUpdateUserProfilesFromMention(Boolean allowUpdateUserProfilesFromMention) {
        this.allowUpdateUserProfilesFromMention = allowUpdateUserProfilesFromMention;
    }

    public String getHerokuDbRowsLimit() {
        return herokuDbRowsLimit;
    }

    public void setHerokuDbRowsLimit(String herokuDbRowsLimit) {
        this.herokuDbRowsLimit = herokuDbRowsLimit;
    }

    public Boolean getSkipFortesting() {
        return skipFortesting;
    }

    public void setSkipFortesting(Boolean skipFortesting) {
        this.skipFortesting = skipFortesting;
    }

    public static class FetchUserList {

        @NotNull
        private Boolean allow;

        @NotNull
        private String name;

        public Boolean getAllow() {
            return allow;
        }

        public void setAllow(Boolean allow) {
            this.allow = allow;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public FetchUserList getFetchUserList() {
        return fetchUserList;
    }

    public void setFetchUserList(FetchUserList fetchUserList) {
        this.fetchUserList = fetchUserList;
    }

    public static class Facade {

        private List<Long> idTwitterToFetchForTweetTest = new ArrayList<Long>();

        private List<Long> idTwitterToFetchForUserControllerTest = new ArrayList<Long>();

        public List<Long> getIdTwitterToFetchForTweetTest() {
            return idTwitterToFetchForTweetTest;
        }

        public void setIdTwitterToFetchForTweetTest(List<Long> idTwitterToFetchForTweetTest) {
            this.idTwitterToFetchForTweetTest = idTwitterToFetchForTweetTest;
        }

        public List<Long> getIdTwitterToFetchForUserControllerTest() {
            return idTwitterToFetchForUserControllerTest;
        }

        public void setIdTwitterToFetchForUserControllerTest(List<Long> idTwitterToFetchForUserControllerTest) {
            this.idTwitterToFetchForUserControllerTest = idTwitterToFetchForUserControllerTest;
        }
    }

    public Facade getFacade() {
        return facade;
    }

    public void setFacade(Facade facade) {
        this.facade = facade;
    }
}
