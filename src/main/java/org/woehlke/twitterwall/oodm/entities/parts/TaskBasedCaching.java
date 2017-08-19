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

    @Column(name=COLUMN_PREFIX+"fetch_userlist_owners")
    private Date fetchUserlistOwners;

    @Column(name=COLUMN_PREFIX+"start_garbage_collection")
    private Date startGarbageCollection;

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
            case UPDATE_MENTIONS_FOR_USERS:
                lastApiCall = updateUsersFromMentions;
                break;
            case FETCH_USERS_FROM_LIST:
                lastApiCall = fetchUsersFromList;
                break;
            case CREATE_TESTDATA_TWEETS:
                lastApiCall = controllerGetTestdataTweets;
                break;
            case CREATE_TESTDATA_USERS:
                lastApiCall = controllerGetTestdataUser;
                break;
            case CREATE_IMPRINT_USER:
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
            case FETCH_USERLIST_OWNERS:
                lastApiCall = fetchUserlistOwners;
                break;
            case GARBAGE_COLLECTION:
                lastApiCall = startGarbageCollection;
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
                case UPDATE_MENTIONS_FOR_USERS:
                    updateUsersFromMentions = lastApiCall;
                    break;
                case FETCH_USERS_FROM_LIST:
                    fetchUsersFromList = lastApiCall;
                    break;
                case CREATE_TESTDATA_TWEETS:
                    controllerGetTestdataTweets = lastApiCall;
                    break;
                case CREATE_TESTDATA_USERS:
                    controllerGetTestdataUser = lastApiCall;
                    break;
                case CREATE_IMPRINT_USER:
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
                case FETCH_USERLIST_OWNERS:
                    fetchUserlistOwners = lastApiCall;
                    break;
                case GARBAGE_COLLECTION:
                    startGarbageCollection = lastApiCall;
                    break;
                default:
                    break;
            }
        }
    }

    public TaskBasedCaching(Date fetchTweetsFromSearch, Date updateTweets, Date updateUsers, Date updateUsersFromMentions, Date fetchUsersFromList, Date controllerGetTestdataTweets, Date controllerGetTestdataUser, Date controllerAddUserForScreenName, Date controllerCreateImprintUser, Date removeOldDataFromStorage, Date fetchFollower, Date fetchFriends, Date getHomeTimeline, Date getUserTimeline, Date getMentions, Date getFavorites, Date getRetweetsOfMe, Date getLists,Date fetchUserlistOwners,Date startGarbageCollection) {
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
        this.fetchUserlistOwners = fetchUserlistOwners;
        this.startGarbageCollection = startGarbageCollection;
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
        this.fetchUserlistOwners = null;
        this.startGarbageCollection = null;
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

    public Date getFetchUserlistOwners() {
        return fetchUserlistOwners;
    }

    public Date getStartGarbageCollection() {
        return startGarbageCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskBasedCaching)) return false;

        TaskBasedCaching that = (TaskBasedCaching) o;

        if (fetchTweetsFromSearch != null ? !fetchTweetsFromSearch.equals(that.fetchTweetsFromSearch) : that.fetchTweetsFromSearch != null)
            return false;
        if (updateTweets != null ? !updateTweets.equals(that.updateTweets) : that.updateTweets != null) return false;
        if (updateUsers != null ? !updateUsers.equals(that.updateUsers) : that.updateUsers != null) return false;
        if (updateUsersFromMentions != null ? !updateUsersFromMentions.equals(that.updateUsersFromMentions) : that.updateUsersFromMentions != null)
            return false;
        if (fetchUsersFromList != null ? !fetchUsersFromList.equals(that.fetchUsersFromList) : that.fetchUsersFromList != null)
            return false;
        if (controllerGetTestdataTweets != null ? !controllerGetTestdataTweets.equals(that.controllerGetTestdataTweets) : that.controllerGetTestdataTweets != null)
            return false;
        if (controllerGetTestdataUser != null ? !controllerGetTestdataUser.equals(that.controllerGetTestdataUser) : that.controllerGetTestdataUser != null)
            return false;
        if (controllerAddUserForScreenName != null ? !controllerAddUserForScreenName.equals(that.controllerAddUserForScreenName) : that.controllerAddUserForScreenName != null)
            return false;
        if (controllerCreateImprintUser != null ? !controllerCreateImprintUser.equals(that.controllerCreateImprintUser) : that.controllerCreateImprintUser != null)
            return false;
        if (removeOldDataFromStorage != null ? !removeOldDataFromStorage.equals(that.removeOldDataFromStorage) : that.removeOldDataFromStorage != null)
            return false;
        if (fetchFollower != null ? !fetchFollower.equals(that.fetchFollower) : that.fetchFollower != null)
            return false;
        if (fetchFriends != null ? !fetchFriends.equals(that.fetchFriends) : that.fetchFriends != null) return false;
        if (getHomeTimeline != null ? !getHomeTimeline.equals(that.getHomeTimeline) : that.getHomeTimeline != null)
            return false;
        if (getUserTimeline != null ? !getUserTimeline.equals(that.getUserTimeline) : that.getUserTimeline != null)
            return false;
        if (getMentions != null ? !getMentions.equals(that.getMentions) : that.getMentions != null) return false;
        if (getFavorites != null ? !getFavorites.equals(that.getFavorites) : that.getFavorites != null) return false;
        if (getRetweetsOfMe != null ? !getRetweetsOfMe.equals(that.getRetweetsOfMe) : that.getRetweetsOfMe != null)
            return false;
        if (getLists != null ? !getLists.equals(that.getLists) : that.getLists != null) return false;
        if (fetchUserlistOwners != null ? !fetchUserlistOwners.equals(that.fetchUserlistOwners) : that.fetchUserlistOwners != null)
            return false;
        return startGarbageCollection != null ? startGarbageCollection.equals(that.startGarbageCollection) : that.startGarbageCollection == null;
    }

    @Override
    public int hashCode() {
        int result = fetchTweetsFromSearch != null ? fetchTweetsFromSearch.hashCode() : 0;
        result = 31 * result + (updateTweets != null ? updateTweets.hashCode() : 0);
        result = 31 * result + (updateUsers != null ? updateUsers.hashCode() : 0);
        result = 31 * result + (updateUsersFromMentions != null ? updateUsersFromMentions.hashCode() : 0);
        result = 31 * result + (fetchUsersFromList != null ? fetchUsersFromList.hashCode() : 0);
        result = 31 * result + (controllerGetTestdataTweets != null ? controllerGetTestdataTweets.hashCode() : 0);
        result = 31 * result + (controllerGetTestdataUser != null ? controllerGetTestdataUser.hashCode() : 0);
        result = 31 * result + (controllerAddUserForScreenName != null ? controllerAddUserForScreenName.hashCode() : 0);
        result = 31 * result + (controllerCreateImprintUser != null ? controllerCreateImprintUser.hashCode() : 0);
        result = 31 * result + (removeOldDataFromStorage != null ? removeOldDataFromStorage.hashCode() : 0);
        result = 31 * result + (fetchFollower != null ? fetchFollower.hashCode() : 0);
        result = 31 * result + (fetchFriends != null ? fetchFriends.hashCode() : 0);
        result = 31 * result + (getHomeTimeline != null ? getHomeTimeline.hashCode() : 0);
        result = 31 * result + (getUserTimeline != null ? getUserTimeline.hashCode() : 0);
        result = 31 * result + (getMentions != null ? getMentions.hashCode() : 0);
        result = 31 * result + (getFavorites != null ? getFavorites.hashCode() : 0);
        result = 31 * result + (getRetweetsOfMe != null ? getRetweetsOfMe.hashCode() : 0);
        result = 31 * result + (getLists != null ? getLists.hashCode() : 0);
        result = 31 * result + (fetchUserlistOwners != null ? fetchUserlistOwners.hashCode() : 0);
        result = 31 * result + (startGarbageCollection != null ? startGarbageCollection.hashCode() : 0);
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
                ", fetchUserlistOwners=" + fetchUserlistOwners +
                ", startGarbageCollection=" + startGarbageCollection +
                '}';
    }

    private final static String COLUMN_PREFIX = "cache_";

    @Transient
    public String getUniqueId() {
        return toString();
    }
}
