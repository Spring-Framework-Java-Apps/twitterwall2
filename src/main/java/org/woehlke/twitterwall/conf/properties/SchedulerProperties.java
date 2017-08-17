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
    private Boolean fetchUsersFromDefinedUserListAllow;

    @NotNull
    private String fetchUsersFromDefinedUserListName;

    @NotNull
    private Boolean removeOldDataFromStorageAllow;

    @NotNull
    private Boolean fetchFollowerAllow;

    @NotNull
    private Boolean fetchFriendsAllow;

    @NotNull
    private Boolean allowGetHomeTimeline;

    @NotNull
    private Boolean allowGetUserTimeline;

    @NotNull
    private Boolean allowGetMentions;

    @NotNull
    private Boolean allowGetFavorites;

    @NotNull
    private Boolean allowGetRetweetsOfMe;

    @NotNull
    private Boolean allowGetLists;

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

    public Boolean getFetchUsersFromDefinedUserListAllow() {
        return fetchUsersFromDefinedUserListAllow;
    }

    public void setFetchUsersFromDefinedUserListAllow(Boolean fetchUsersFromDefinedUserListAllow) {
        this.fetchUsersFromDefinedUserListAllow = fetchUsersFromDefinedUserListAllow;
    }

    public String getFetchUsersFromDefinedUserListName() {
        return fetchUsersFromDefinedUserListName;
    }

    public void setFetchUsersFromDefinedUserListName(String fetchUsersFromDefinedUserListName) {
        this.fetchUsersFromDefinedUserListName = fetchUsersFromDefinedUserListName;
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

    public Boolean getAllowGetHomeTimeline() {
        return allowGetHomeTimeline;
    }

    public void setAllowGetHomeTimeline(Boolean allowGetHomeTimeline) {
        this.allowGetHomeTimeline = allowGetHomeTimeline;
    }

    public Boolean getAllowGetUserTimeline() {
        return allowGetUserTimeline;
    }

    public void setAllowGetUserTimeline(Boolean allowGetUserTimeline) {
        this.allowGetUserTimeline = allowGetUserTimeline;
    }

    public Boolean getAllowGetMentions() {
        return allowGetMentions;
    }

    public void setAllowGetMentions(Boolean allowGetMentions) {
        this.allowGetMentions = allowGetMentions;
    }

    public Boolean getAllowGetFavorites() {
        return allowGetFavorites;
    }

    public void setAllowGetFavorites(Boolean allowGetFavorites) {
        this.allowGetFavorites = allowGetFavorites;
    }

    public Boolean getAllowGetRetweetsOfMe() {
        return allowGetRetweetsOfMe;
    }

    public void setAllowGetRetweetsOfMe(Boolean allowGetRetweetsOfMe) {
        this.allowGetRetweetsOfMe = allowGetRetweetsOfMe;
    }

    public Boolean getAllowGetLists() {
        return allowGetLists;
    }

    public void setAllowGetLists(Boolean allowGetLists) {
        this.allowGetLists = allowGetLists;
    }
}
