package org.woehlke.twitterwall.oodm.entities.parts;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Validated
@Embeddable
public class TwitterApiCaching implements Serializable {

    @Column(name=COLUMN_PREFIX+"fetch_tweets_from_twitter_search")
    private Date fetchTweetsFromTwitterSearch;

    @Column(name=COLUMN_PREFIX+"update_tweets")
    private Date updateTweets;

    @Column(name=COLUMN_PREFIX+"update_user_profiles")
    private Date updateUserProfiles;

    @Column(name=COLUMN_PREFIX+"update_user_profiles_from_mentions")
    private Date updateUserProfilesFromMentions;

    @Column(name=COLUMN_PREFIX+"fetch_users_from_defined_user_list")
    private Date fetchUsersFromDefinedUserList;

    @Column(name=COLUMN_PREFIX+"controller_get_testdata_tweets")
    private Date controllerGetTestdataTweets;

    @Column(name=COLUMN_PREFIX+"controller_get_testdata_user")
    private Date controllerGetTestdataUser;

    @Column(name=COLUMN_PREFIX+"controller_add_user_for_screen_name")
    private Date controllerAddUserForScreenName;

    @Column(name=COLUMN_PREFIX+"controller_create_imprint_user")
    private Date controllerCreateImprintUser;

    @Transient
    public Boolean isCached(TaskType taskType, long timeToLive){
        Date lastApiCall = null;
        switch (taskType){
            case FETCH_TWEETS_FROM_TWITTER_SEARCH:
                lastApiCall = fetchTweetsFromTwitterSearch;
                break;
            case UPDATE_TWEETS:
                lastApiCall = updateTweets;
                break;
            case UPDATE_USER_PROFILES:
                lastApiCall = updateUserProfiles;
                break;
            case UPDATE_USER_PROFILES_FROM_MENTIONS:
                lastApiCall = updateUserProfilesFromMentions;
                break;
            case FETCH_USERS_FROM_DEFINED_USER_LIST:
                lastApiCall = fetchUsersFromDefinedUserList;
                break;
            case CONTROLLER_GET_TESTDATA_TWEETS:
                lastApiCall = controllerGetTestdataTweets;
                break;
            case CONTROLLER_GET_TESTDATA_USER:
                lastApiCall = controllerGetTestdataUser;
                break;
            case CONTROLLER_ADD_USER_FOR_SCREEN_NAME:
                lastApiCall = controllerAddUserForScreenName;
                break;
            case CONTROLLER_CREATE_IMPRINT_USER:
                lastApiCall = controllerCreateImprintUser;
                break;
            default: break;
        }
        if(lastApiCall == null){
            return false;
        }
        long lastApiCallPlusTimeToLive = lastApiCall.getTime() + timeToLive;
        long now = (new Date()).getTime();
        return now < lastApiCallPlusTimeToLive;
    }

    public void store(TaskType taskType){
        Date lastApiCall = new Date();
        switch (taskType){
            case FETCH_TWEETS_FROM_TWITTER_SEARCH:
                fetchTweetsFromTwitterSearch = lastApiCall;
                break;
            case UPDATE_TWEETS:
                updateTweets = lastApiCall;
                break;
            case UPDATE_USER_PROFILES:
                updateUserProfiles = lastApiCall;
                break;
            case UPDATE_USER_PROFILES_FROM_MENTIONS:
                updateUserProfilesFromMentions = lastApiCall;
                break;
            case FETCH_USERS_FROM_DEFINED_USER_LIST:
                fetchUsersFromDefinedUserList = lastApiCall;
                break;
            case CONTROLLER_GET_TESTDATA_TWEETS:
                controllerGetTestdataTweets = lastApiCall;
                break;
            case CONTROLLER_GET_TESTDATA_USER:
                controllerGetTestdataUser = lastApiCall;
                break;
            case CONTROLLER_ADD_USER_FOR_SCREEN_NAME:
                controllerAddUserForScreenName = lastApiCall;
                break;
            case CONTROLLER_CREATE_IMPRINT_USER:
                controllerCreateImprintUser = lastApiCall;
                break;
            default: break;
        }
    }

    public TwitterApiCaching(Date fetchTweetsFromTwitterSearch, Date updateTweets, Date updateUserProfiles, Date updateUserProfilesFromMentions, Date fetchUsersFromDefinedUserList, Date controllerGetTestdataTweets, Date controllerGetTestdataUser, Date controllerAddUserForScreenName, Date controllerCreateImprintUser) {
        this.fetchTweetsFromTwitterSearch = fetchTweetsFromTwitterSearch;
        this.updateTweets = updateTweets;
        this.updateUserProfiles = updateUserProfiles;
        this.updateUserProfilesFromMentions = updateUserProfilesFromMentions;
        this.fetchUsersFromDefinedUserList = fetchUsersFromDefinedUserList;
        this.controllerGetTestdataTweets = controllerGetTestdataTweets;
        this.controllerGetTestdataUser = controllerGetTestdataUser;
        this.controllerAddUserForScreenName = controllerAddUserForScreenName;
        this.controllerCreateImprintUser = controllerCreateImprintUser;
    }

    public TwitterApiCaching() {
        this.fetchTweetsFromTwitterSearch = null;
        this.updateTweets = null;
        this.updateUserProfiles = null;
        this.updateUserProfilesFromMentions = null;
        this.fetchUsersFromDefinedUserList = null;
        this.controllerGetTestdataTweets = null;
        this.controllerGetTestdataUser = null;
        this.controllerAddUserForScreenName = null;
        this.controllerCreateImprintUser = null;
    }

    public Date getFetchTweetsFromTwitterSearch() {
        return fetchTweetsFromTwitterSearch;
    }

    public Date getUpdateTweets() {
        return updateTweets;
    }

    public Date getUpdateUserProfiles() {
        return updateUserProfiles;
    }

    public Date getUpdateUserProfilesFromMentions() {
        return updateUserProfilesFromMentions;
    }

    public Date getFetchUsersFromDefinedUserList() {
        return fetchUsersFromDefinedUserList;
    }

    public Date getControllerGetTestdataTweets() {
        return controllerGetTestdataTweets;
    }

    public Date getControllerGetTestdataUser() {
        return controllerGetTestdataUser;
    }

    public Date getControllerAddUserForScreenName() {
        return controllerAddUserForScreenName;
    }

    public Date getControllerCreateImprintUser() {
        return controllerCreateImprintUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwitterApiCaching)) return false;

        TwitterApiCaching that = (TwitterApiCaching) o;

        if (fetchTweetsFromTwitterSearch != null ? !fetchTweetsFromTwitterSearch.equals(that.fetchTweetsFromTwitterSearch) : that.fetchTweetsFromTwitterSearch != null)
            return false;
        if (updateTweets != null ? !updateTweets.equals(that.updateTweets) : that.updateTweets != null) return false;
        if (updateUserProfiles != null ? !updateUserProfiles.equals(that.updateUserProfiles) : that.updateUserProfiles != null)
            return false;
        if (updateUserProfilesFromMentions != null ? !updateUserProfilesFromMentions.equals(that.updateUserProfilesFromMentions) : that.updateUserProfilesFromMentions != null)
            return false;
        if (fetchUsersFromDefinedUserList != null ? !fetchUsersFromDefinedUserList.equals(that.fetchUsersFromDefinedUserList) : that.fetchUsersFromDefinedUserList != null)
            return false;
        if (controllerGetTestdataTweets != null ? !controllerGetTestdataTweets.equals(that.controllerGetTestdataTweets) : that.controllerGetTestdataTweets != null)
            return false;
        if (controllerGetTestdataUser != null ? !controllerGetTestdataUser.equals(that.controllerGetTestdataUser) : that.controllerGetTestdataUser != null)
            return false;
        if (controllerAddUserForScreenName != null ? !controllerAddUserForScreenName.equals(that.controllerAddUserForScreenName) : that.controllerAddUserForScreenName != null)
            return false;
        return controllerCreateImprintUser != null ? controllerCreateImprintUser.equals(that.controllerCreateImprintUser) : that.controllerCreateImprintUser == null;
    }

    @Override
    public int hashCode() {
        int result = fetchTweetsFromTwitterSearch != null ? fetchTweetsFromTwitterSearch.hashCode() : 0;
        result = 31 * result + (updateTweets != null ? updateTweets.hashCode() : 0);
        result = 31 * result + (updateUserProfiles != null ? updateUserProfiles.hashCode() : 0);
        result = 31 * result + (updateUserProfilesFromMentions != null ? updateUserProfilesFromMentions.hashCode() : 0);
        result = 31 * result + (fetchUsersFromDefinedUserList != null ? fetchUsersFromDefinedUserList.hashCode() : 0);
        result = 31 * result + (controllerGetTestdataTweets != null ? controllerGetTestdataTweets.hashCode() : 0);
        result = 31 * result + (controllerGetTestdataUser != null ? controllerGetTestdataUser.hashCode() : 0);
        result = 31 * result + (controllerAddUserForScreenName != null ? controllerAddUserForScreenName.hashCode() : 0);
        result = 31 * result + (controllerCreateImprintUser != null ? controllerCreateImprintUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TwitterApiCaching{" +
                "fetchTweetsFromTwitterSearch=" + fetchTweetsFromTwitterSearch +
                ", updateTweets=" + updateTweets +
                ", updateUserProfiles=" + updateUserProfiles +
                ", updateUserProfilesFromMentions=" + updateUserProfilesFromMentions +
                ", fetchUsersFromDefinedUserList=" + fetchUsersFromDefinedUserList +
                ", controllerGetTestdataTweets=" + controllerGetTestdataTweets +
                ", controllerGetTestdataUser=" + controllerGetTestdataUser +
                ", controllerAddUserForScreenName=" + controllerAddUserForScreenName +
                ", controllerCreateImprintUser=" + controllerCreateImprintUser +
                '}';
    }

    private final static String COLUMN_PREFIX = "remote_api_cache_";
}
