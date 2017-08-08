package org.woehlke.twitterwall.conf.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
@ConfigurationProperties(prefix="twitterwall.scheduler")
public class SchedulerProperties {

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

    @NotNull
    private Boolean fetchUserListAllow;

    @NotNull
    private String fetchUserListName;

    @NotNull
    private Boolean removeOldDataFromStorageAllow;

    @NotNull
    private Boolean fetchFollowerAllow;

    @NotNull
    private Boolean fetchFriendsAllow;

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

    public Boolean getFetchUserListAllow() {
        return fetchUserListAllow;
    }

    public void setFetchUserListAllow(Boolean fetchUserListAllow) {
        this.fetchUserListAllow = fetchUserListAllow;
    }

    public String getFetchUserListName() {
        return fetchUserListName;
    }

    public void setFetchUserListName(String fetchUserListName) {
        this.fetchUserListName = fetchUserListName;
    }

    public Boolean getRemoveOldDataFromStorageAllow() {
        return removeOldDataFromStorageAllow;
    }

    public void setRemoveOldDataFromStorageAllow(Boolean removeOldDataFromStorageAllow) {
        this.removeOldDataFromStorageAllow = removeOldDataFromStorageAllow;
    }

    public void setFetchFollowerAllow(Boolean fetchFollowerAllow) {
        this.fetchFollowerAllow = fetchFollowerAllow;
    }

    public Boolean getFetchFollowerAllow() {
        return fetchFollowerAllow;
    }

    public void setFetchFriendsAllow(Boolean fetchFriendsAllow) {
        this.fetchFriendsAllow = fetchFriendsAllow;
    }

    public Boolean getFetchFriendsAllow() {
        return fetchFriendsAllow;
    }
}
