package org.woehlke.twitterwall.oodm.entities.parts;

import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.entities.Task;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Validated
@Embeddable
public class TaskBasedCaching implements Serializable {

    @Column(name=COLUMN_PREFIX+"fetch_tweets_from_search")
    private Date fetchTweetsFromSearch;

    @Column(name=COLUMN_PREFIX+"update_tweets")
    private Date updateTweets;

    @Column(name=COLUMN_PREFIX+"update_users")
    private Date updateUsers;

    @Column(name=COLUMN_PREFIX+"update_users_from_mentions")
    private Date updateUsersFromMentions;

    @Column(name=COLUMN_PREFIX+"fetch_users_from_list")
    private Date fetchUsersFromList;

    @Column(name=COLUMN_PREFIX+"controller_create_testdata_tweets")
    private Date controllerGetTestdataTweets;

    @Column(name=COLUMN_PREFIX+"controller_create_testdata_users")
    private Date controllerGetTestdataUser;

    @Column(name=COLUMN_PREFIX+"controller_add_user_for_screen_name")
    private Date controllerAddUserForScreenName;

    @Column(name=COLUMN_PREFIX+"controller_create_imprint_user")
    private Date controllerCreateImprintUser;

    @Column(name=COLUMN_PREFIX+"remove_old_data_from_storage")
    private Date removeOldDataFromStorage;

    @Column(name=COLUMN_PREFIX+"fetch_follower")
    private Date fetchFollower;

    @Column(name=COLUMN_PREFIX+"fetch_friends")
    private Date fetchFriends;

    @Column(name=COLUMN_PREFIX+"fetch_home_timeline")
    private Date getHomeTimeline;

    @Column(name=COLUMN_PREFIX+"fetch_user_timeline")
    private Date getUserTimeline;

    @Column(name=COLUMN_PREFIX+"fetch_mentions")
    private Date getMentions;

    @Column(name=COLUMN_PREFIX+"fetch_favorites")
    private Date getFavorites;

    @Column(name=COLUMN_PREFIX+"fetch_retweets_of_me")
    private Date getRetweetsOfMe;

    @Column(name=COLUMN_PREFIX+"fetch_lists")
    private Date getLists;

    @Transient
    public Boolean isCached(TaskType taskType, long timeToLive){
        Date lastApiCall = null;
        switch (taskType){
            case FETCH_TWEETS_FROM_SEARCH:
                lastApiCall = fetchTweetsFromSearch;
                break;
            case UPDATE_TWEETS:
                lastApiCall = updateTweets;
                break;
            case UPDATE_USERS:
                lastApiCall = updateUsers;
                break;
            case UPDATE_USERS_FROM_MENTIONS:
                lastApiCall = updateUsersFromMentions;
                break;
            case FETCH_USERS_FROM_LIST:
                lastApiCall = fetchUsersFromList;
                break;
            case CONTROLLER_CREATE_TESTDATA_TWEETS:
                lastApiCall = controllerGetTestdataTweets;
                break;
            case CONTROLLER_CREATE_TESTDATA_USERS:
                lastApiCall = controllerGetTestdataUser;
                break;
            case CONTROLLER_ADD_USER_FOR_SCREEN_NAME:
                lastApiCall = controllerAddUserForScreenName;
                break;
            case CONTROLLER_CREATE_IMPRINT_USER:
                lastApiCall = controllerCreateImprintUser;
                break;
            case REMOVE_OLD_DATA_FROM_STORAGE:
                lastApiCall = removeOldDataFromStorage;
                break;
            case FETCH_FOLLOWER:
                lastApiCall = fetchFollower;
                break;
            case FETCH_FRIENDS:
                lastApiCall = fetchFriends;
                break;
            case FETCH_HOME_TIMELINE:
                lastApiCall = getHomeTimeline;
                break;
            case FETCH_USER_TIMELINE:
                lastApiCall = getUserTimeline;
                break;
            case FETCH_MENTIONS:
                lastApiCall = getMentions;
                break;
            case FETCH_FAVORITES:
                lastApiCall = getFavorites;
                break;
            case FETCH_RETWEETS_OF_ME:
                lastApiCall = getRetweetsOfMe;
                break;
            case FETCH_LISTS:
                lastApiCall = getLists;
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

    @Transient
    public void store(Task task) {
        if (task != null) {
            TaskType taskType = task.getTaskType();
            store(taskType);
        }
    }

    @Transient
    public void store(TaskType taskType){
        if(taskType != null) {
            Date lastApiCall = new Date();
            switch (taskType) {
                case FETCH_TWEETS_FROM_SEARCH:
                    fetchTweetsFromSearch = lastApiCall;
                    break;
                case UPDATE_TWEETS:
                    updateTweets = lastApiCall;
                    break;
                case UPDATE_USERS:
                    updateUsers = lastApiCall;
                    break;
                case UPDATE_USERS_FROM_MENTIONS:
                    updateUsersFromMentions = lastApiCall;
                    break;
                case FETCH_USERS_FROM_LIST:
                    fetchUsersFromList = lastApiCall;
                    break;
                case CONTROLLER_CREATE_TESTDATA_TWEETS:
                    controllerGetTestdataTweets = lastApiCall;
                    break;
                case CONTROLLER_CREATE_TESTDATA_USERS:
                    controllerGetTestdataUser = lastApiCall;
                    break;
                case CONTROLLER_ADD_USER_FOR_SCREEN_NAME:
                    controllerAddUserForScreenName = lastApiCall;
                    break;
                case CONTROLLER_CREATE_IMPRINT_USER:
                    controllerCreateImprintUser = lastApiCall;
                    break;
                case REMOVE_OLD_DATA_FROM_STORAGE:
                    removeOldDataFromStorage = lastApiCall;
                    break;
                case FETCH_FOLLOWER:
                    fetchFollower = lastApiCall;
                    break;
                case FETCH_FRIENDS:
                    fetchFriends = lastApiCall;
                    break;
                case FETCH_HOME_TIMELINE:
                    getHomeTimeline = lastApiCall;
                    break;
                case FETCH_USER_TIMELINE:
                    getUserTimeline = lastApiCall;
                    break;
                case FETCH_MENTIONS:
                    getMentions = lastApiCall;
                    break;
                case FETCH_FAVORITES:
                    getFavorites = lastApiCall;
                    break;
                case FETCH_RETWEETS_OF_ME:
                    getRetweetsOfMe = lastApiCall;
                    break;
                case FETCH_LISTS:
                    getLists = lastApiCall;
                default:
                    break;
            }
        }
    }

    public TaskBasedCaching(Date fetchTweetsFromSearch, Date updateTweets, Date updateUsers, Date updateUsersFromMentions, Date fetchUsersFromList, Date controllerGetTestdataTweets, Date controllerGetTestdataUser, Date controllerAddUserForScreenName, Date controllerCreateImprintUser, Date removeOldDataFromStorage, Date fetchFollower, Date fetchFriends, Date getHomeTimeline, Date getUserTimeline, Date getMentions, Date getFavorites, Date getRetweetsOfMe, Date getLists) {
        this.fetchTweetsFromSearch = fetchTweetsFromSearch;
        this.updateTweets = updateTweets;
        this.updateUsers = updateUsers;
        this.updateUsersFromMentions = updateUsersFromMentions;
        this.fetchUsersFromList = fetchUsersFromList;
        this.controllerGetTestdataTweets = controllerGetTestdataTweets;
        this.controllerGetTestdataUser = controllerGetTestdataUser;
        this.controllerAddUserForScreenName = controllerAddUserForScreenName;
        this.controllerCreateImprintUser = controllerCreateImprintUser;
        this.removeOldDataFromStorage = removeOldDataFromStorage;
        this.fetchFollower = fetchFollower;
        this.fetchFriends = fetchFriends;
        this.getHomeTimeline = getHomeTimeline;
        this.getUserTimeline = getUserTimeline;
        this.getMentions = getMentions;
        this.getFavorites = getFavorites;
        this.getRetweetsOfMe = getRetweetsOfMe;
        this.getLists = getLists;
    }

    public TaskBasedCaching() {
        this.fetchTweetsFromSearch = null;
        this.updateTweets = null;
        this.updateUsers = null;
        this.updateUsersFromMentions = null;
        this.fetchUsersFromList = null;
        this.controllerGetTestdataTweets = null;
        this.controllerGetTestdataUser = null;
        this.controllerAddUserForScreenName = null;
        this.controllerCreateImprintUser = null;
        this.removeOldDataFromStorage = null;
        this.fetchFollower = null;
        this.getHomeTimeline = null;
        this.getUserTimeline = null;
        this.getMentions = null;
        this.getFavorites = null;
        this.getRetweetsOfMe = null;
        this.getLists = null;
    }

    public Date getFetchTweetsFromSearch() {
        return fetchTweetsFromSearch;
    }

    public Date getUpdateTweets() {
        return updateTweets;
    }

    public Date getUpdateUsers() {
        return updateUsers;
    }

    public Date getUpdateUsersFromMentions() {
        return updateUsersFromMentions;
    }

    public Date getFetchUsersFromList() {
        return fetchUsersFromList;
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

    public Date getRemoveOldDataFromStorage() {
        return removeOldDataFromStorage;
    }

    public Date getFetchFollower() {
        return fetchFollower;
    }

    public Date getFetchFriends() {
        return fetchFriends;
    }

    public Date getGetHomeTimeline() {
        return getHomeTimeline;
    }

    public Date getGetUserTimeline() {
        return getUserTimeline;
    }

    public Date getGetMentions() {
        return getMentions;
    }

    public Date getGetFavorites() {
        return getFavorites;
    }

    public Date getGetRetweetsOfMe() {
        return getRetweetsOfMe;
    }

    public Date getGetLists() {
        return getLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskBasedCaching)) return false;

        TaskBasedCaching that = (TaskBasedCaching) o;

        if (getFetchTweetsFromSearch() != null ? !getFetchTweetsFromSearch().equals(that.getFetchTweetsFromSearch()) : that.getFetchTweetsFromSearch() != null)
            return false;
        if (getUpdateTweets() != null ? !getUpdateTweets().equals(that.getUpdateTweets()) : that.getUpdateTweets() != null)
            return false;
        if (getUpdateUsers() != null ? !getUpdateUsers().equals(that.getUpdateUsers()) : that.getUpdateUsers() != null)
            return false;
        if (getUpdateUsersFromMentions() != null ? !getUpdateUsersFromMentions().equals(that.getUpdateUsersFromMentions()) : that.getUpdateUsersFromMentions() != null)
            return false;
        if (getFetchUsersFromList() != null ? !getFetchUsersFromList().equals(that.getFetchUsersFromList()) : that.getFetchUsersFromList() != null)
            return false;
        if (getControllerGetTestdataTweets() != null ? !getControllerGetTestdataTweets().equals(that.getControllerGetTestdataTweets()) : that.getControllerGetTestdataTweets() != null)
            return false;
        if (getControllerGetTestdataUser() != null ? !getControllerGetTestdataUser().equals(that.getControllerGetTestdataUser()) : that.getControllerGetTestdataUser() != null)
            return false;
        if (getControllerAddUserForScreenName() != null ? !getControllerAddUserForScreenName().equals(that.getControllerAddUserForScreenName()) : that.getControllerAddUserForScreenName() != null)
            return false;
        if (getControllerCreateImprintUser() != null ? !getControllerCreateImprintUser().equals(that.getControllerCreateImprintUser()) : that.getControllerCreateImprintUser() != null)
            return false;
        if (getRemoveOldDataFromStorage() != null ? !getRemoveOldDataFromStorage().equals(that.getRemoveOldDataFromStorage()) : that.getRemoveOldDataFromStorage() != null)
            return false;
        if (getFetchFollower() != null ? !getFetchFollower().equals(that.getFetchFollower()) : that.getFetchFollower() != null)
            return false;
        if (getFetchFriends() != null ? !getFetchFriends().equals(that.getFetchFriends()) : that.getFetchFriends() != null)
            return false;
        if (getGetHomeTimeline() != null ? !getGetHomeTimeline().equals(that.getGetHomeTimeline()) : that.getGetHomeTimeline() != null)
            return false;
        if (getGetUserTimeline() != null ? !getGetUserTimeline().equals(that.getGetUserTimeline()) : that.getGetUserTimeline() != null)
            return false;
        if (getGetMentions() != null ? !getGetMentions().equals(that.getGetMentions()) : that.getGetMentions() != null)
            return false;
        if (getGetFavorites() != null ? !getGetFavorites().equals(that.getGetFavorites()) : that.getGetFavorites() != null)
            return false;
        if (getGetRetweetsOfMe() != null ? !getGetRetweetsOfMe().equals(that.getGetRetweetsOfMe()) : that.getGetRetweetsOfMe() != null)
            return false;
        return getGetLists() != null ? getGetLists().equals(that.getGetLists()) : that.getGetLists() == null;
    }

    @Override
    public int hashCode() {
        int result = getFetchTweetsFromSearch() != null ? getFetchTweetsFromSearch().hashCode() : 0;
        result = 31 * result + (getUpdateTweets() != null ? getUpdateTweets().hashCode() : 0);
        result = 31 * result + (getUpdateUsers() != null ? getUpdateUsers().hashCode() : 0);
        result = 31 * result + (getUpdateUsersFromMentions() != null ? getUpdateUsersFromMentions().hashCode() : 0);
        result = 31 * result + (getFetchUsersFromList() != null ? getFetchUsersFromList().hashCode() : 0);
        result = 31 * result + (getControllerGetTestdataTweets() != null ? getControllerGetTestdataTweets().hashCode() : 0);
        result = 31 * result + (getControllerGetTestdataUser() != null ? getControllerGetTestdataUser().hashCode() : 0);
        result = 31 * result + (getControllerAddUserForScreenName() != null ? getControllerAddUserForScreenName().hashCode() : 0);
        result = 31 * result + (getControllerCreateImprintUser() != null ? getControllerCreateImprintUser().hashCode() : 0);
        result = 31 * result + (getRemoveOldDataFromStorage() != null ? getRemoveOldDataFromStorage().hashCode() : 0);
        result = 31 * result + (getFetchFollower() != null ? getFetchFollower().hashCode() : 0);
        result = 31 * result + (getFetchFriends() != null ? getFetchFriends().hashCode() : 0);
        result = 31 * result + (getGetHomeTimeline() != null ? getGetHomeTimeline().hashCode() : 0);
        result = 31 * result + (getGetUserTimeline() != null ? getGetUserTimeline().hashCode() : 0);
        result = 31 * result + (getGetMentions() != null ? getGetMentions().hashCode() : 0);
        result = 31 * result + (getGetFavorites() != null ? getGetFavorites().hashCode() : 0);
        result = 31 * result + (getGetRetweetsOfMe() != null ? getGetRetweetsOfMe().hashCode() : 0);
        result = 31 * result + (getGetLists() != null ? getGetLists().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskBasedCaching{" +
            "fetchTweetsFromSearch=" + fetchTweetsFromSearch +
            ", updateTweets=" + updateTweets +
            ", updateUsers=" + updateUsers +
            ", updateUsersFromMentions=" + updateUsersFromMentions +
            ", fetchUsersFromList=" + fetchUsersFromList +
            ", controllerGetTestdataTweets=" + controllerGetTestdataTweets +
            ", controllerGetTestdataUser=" + controllerGetTestdataUser +
            ", controllerAddUserForScreenName=" + controllerAddUserForScreenName +
            ", controllerCreateImprintUser=" + controllerCreateImprintUser +
            ", removeOldDataFromStorage=" + removeOldDataFromStorage +
            ", fetchFollower=" + fetchFollower +
            ", fetchFriends=" + fetchFriends +
            ", getHomeTimeline=" + getHomeTimeline +
            ", getUserTimeline=" + getUserTimeline +
            ", getMentions=" + getMentions +
            ", getFavorites=" + getFavorites +
            ", getRetweetsOfMe=" + getRetweetsOfMe +
            ", getLists=" + getLists +
            '}';
    }

    private final static String COLUMN_PREFIX = "cache_";

    @Transient
    public String getUniqueId() {
        return toString();
    }
}
