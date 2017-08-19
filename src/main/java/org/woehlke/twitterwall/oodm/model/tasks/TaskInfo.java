package org.woehlke.twitterwall.oodm.model.tasks;

import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.model.Task;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by tw on 10.07.17.
 */
@Validated
@Embeddable
public class TaskInfo implements Serializable {

    @NotNull
    @Column(nullable = false,name="fetch_tweets_from_search")
    private Boolean fetchTweetsFromSearch = false;

    @NotNull
    @Column(nullable = false,name="update_tweets")
    private Boolean updateTweets = false;

    @NotNull
    @Column(nullable = false,name="update_users")
    private Boolean updatedUsers = false;

    @NotNull
    @Column(nullable = false,name="update_users_from_mentions")
    private Boolean updateUsersFromMentions = false;

    @NotNull
    @Column(nullable = false,name="fetch_users_from_list")
    private Boolean fetchUsersFromList = false;

    @NotNull
    @Column(nullable = false,name="controller_create_testdata_tweets")
    private Boolean controllerCreateTestdataTweets = false;

    @NotNull
    @Column(nullable = false,name="controller_create_testdata_users")
    private Boolean controllerCreateTestdataUsers = false;

    @NotNull
    @Column(nullable = false,name="controller_create_imprint_user")
    private Boolean controllerCreateImprintUser = false;

    @NotNull
    @Column(nullable = false,name="remove_old_data_from_storage")
    private Boolean removeOldDataFromStorage = false;

    @NotNull
    @Column(nullable = false,name="fetch_follower")
    private Boolean fetchFollower = false;

    @NotNull
    @Column(nullable = false,name="fetch_friends")
    private Boolean fetchFriends = false;

    @NotNull
    @Column(nullable = false,name="fetch_home_timeline")
    private Boolean getHomeTimeline  = false;

    @NotNull
    @Column(nullable = false,name="fetch_user_timeline")
    private Boolean getUserTimeline = false;

    @NotNull
    @Column(nullable = false,name="fetch_mentions")
    private Boolean getMentions = false;

    @NotNull
    @Column(nullable = false,name="fetch_favorites")
    private Boolean getFavorites = false;

    @NotNull
    @Column(nullable = false,name="fetch_retweets_of_me")
    private Boolean getRetweetsOfMe = false;

    @NotNull
    @Column(nullable = false,name="fetch_lists")
    private Boolean getLists = false;

    @NotNull
    @Column(nullable = false,name="fetch_userlist_owners")
    private Boolean fetchUserlistOwners = false;

    @NotNull
    @Column(nullable = false,name="start_garbage_collection")
    private Boolean startGarbageCollection = false;

    @Transient
    public void setTaskInfoFromTask(Task task) {
        if(task!=null) {
            TaskType useCase = task.getTaskType();
            switch (useCase) {
                case FETCH_TWEETS_FROM_SEARCH:
                    this.fetchTweetsFromSearch = true;
                    break;
                case UPDATE_TWEETS:
                    this.updateTweets = true;
                    break;
                case UPDATE_USERS:
                    this.updatedUsers = true;
                    break;
                case UPDATE_MENTIONS_FOR_USERS:
                    this.updateUsersFromMentions = true;
                    break;
                case FETCH_USERS_FROM_LIST:
                    this.fetchUsersFromList = true;
                    break;
                case CREATE_TESTDATA_TWEETS:
                    this.controllerCreateTestdataTweets = true;
                    break;
                case CREATE_TESTDATA_USERS:
                    this.controllerCreateTestdataUsers = true;
                    break;
                case CREATE_IMPRINT_USER:
                    controllerCreateImprintUser = true;
                    break;
                case REMOVE_OLD_DATA_FROM_STORAGE:
                    removeOldDataFromStorage = true;
                    break;
                case FETCH_FOLLOWER:
                    fetchFollower = true;
                    break;
                case FETCH_FRIENDS:
                    fetchFriends = true;
                    break;
                case FETCH_HOME_TIMELINE:
                    getHomeTimeline = true;
                    break;
                case FETCH_USER_TIMELINE:
                    getUserTimeline = true;
                    break;
                case FETCH_MENTIONS:
                    getMentions = true;
                    break;
                case FETCH_FAVORITES:
                    getFavorites = true;
                    break;
                case FETCH_RETWEETS_OF_ME:
                    getRetweetsOfMe = true;
                    break;
                case FETCH_LISTS:
                    getLists = true;
                    break;
                case FETCH_USERLIST_OWNERS:
                    fetchUserlistOwners = true;
                    break;
                case GARBAGE_COLLECTION:
                    startGarbageCollection = true;
                    break;
                default:
                    break;
            }
        }
    }

    public TaskInfo() {
    }

    public TaskInfo(Boolean fetchTweetsFromSearch, Boolean updateTweets, Boolean updatedUsers, Boolean updateUsersFromMentions, Boolean fetchUsersFromList, Boolean controllerCreateTestdataTweets, Boolean controllerCreateTestdataUsers, Boolean controllerCreateImprintUser, Boolean removeOldDataFromStorage, Boolean fetchFollower, Boolean fetchFriends,Boolean fetchUserlistOwners) {
        this.fetchTweetsFromSearch = fetchTweetsFromSearch;
        this.updateTweets = updateTweets;
        this.updatedUsers = updatedUsers;
        this.updateUsersFromMentions = updateUsersFromMentions;
        this.fetchUsersFromList = fetchUsersFromList;
        this.controllerCreateTestdataTweets = controllerCreateTestdataTweets;
        this.controllerCreateTestdataUsers = controllerCreateTestdataUsers;
        this.controllerCreateImprintUser = controllerCreateImprintUser;
        this.removeOldDataFromStorage = removeOldDataFromStorage;
        this.fetchFollower = fetchFollower;
        this.fetchFriends = fetchFriends;
        this.fetchUserlistOwners = fetchUserlistOwners;
    }

    public Boolean getFetchTweetsFromSearch() {
        return fetchTweetsFromSearch;
    }

    public Boolean getUpdateTweets() {
        return updateTweets;
    }

    public Boolean getUpdatedUsers() {
        return updatedUsers;
    }

    public Boolean getUpdateUsersFromMentions() {
        return updateUsersFromMentions;
    }

    public Boolean getFetchUsersFromList() {
        return fetchUsersFromList;
    }

    public Boolean getControllerCreateTestdataTweets() {
        return controllerCreateTestdataTweets;
    }

    public Boolean getControllerCreateTestdataUsers() {
        return controllerCreateTestdataUsers;
    }

    public Boolean getControllerCreateImprintUser() {
        return controllerCreateImprintUser;
    }

    public Boolean getRemoveOldDataFromStorage() {
        return removeOldDataFromStorage;
    }

    public Boolean getFetchFollower() {
        return fetchFollower;
    }

    public Boolean getFetchFriends() {
        return fetchFriends;
    }

    public Boolean getGetHomeTimeline() {
        return getHomeTimeline;
    }

    public Boolean getGetUserTimeline() {
        return getUserTimeline;
    }

    public Boolean getGetMentions() {
        return getMentions;
    }

    public Boolean getGetFavorites() {
        return getFavorites;
    }

    public Boolean getGetRetweetsOfMe() {
        return getRetweetsOfMe;
    }

    public Boolean getGetLists() {
        return getLists;
    }

    public Boolean getFetchUserlistOwners() {
        return fetchUserlistOwners;
    }

    public Boolean getStartGarbageCollection() {
        return startGarbageCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskInfo)) return false;

        TaskInfo taskInfo = (TaskInfo) o;

        if (fetchTweetsFromSearch != null ? !fetchTweetsFromSearch.equals(taskInfo.fetchTweetsFromSearch) : taskInfo.fetchTweetsFromSearch != null)
            return false;
        if (updateTweets != null ? !updateTweets.equals(taskInfo.updateTweets) : taskInfo.updateTweets != null)
            return false;
        if (updatedUsers != null ? !updatedUsers.equals(taskInfo.updatedUsers) : taskInfo.updatedUsers != null)
            return false;
        if (updateUsersFromMentions != null ? !updateUsersFromMentions.equals(taskInfo.updateUsersFromMentions) : taskInfo.updateUsersFromMentions != null)
            return false;
        if (fetchUsersFromList != null ? !fetchUsersFromList.equals(taskInfo.fetchUsersFromList) : taskInfo.fetchUsersFromList != null)
            return false;
        if (controllerCreateTestdataTweets != null ? !controllerCreateTestdataTweets.equals(taskInfo.controllerCreateTestdataTweets) : taskInfo.controllerCreateTestdataTweets != null)
            return false;
        if (controllerCreateTestdataUsers != null ? !controllerCreateTestdataUsers.equals(taskInfo.controllerCreateTestdataUsers) : taskInfo.controllerCreateTestdataUsers != null)
            return false;
        if (controllerCreateImprintUser != null ? !controllerCreateImprintUser.equals(taskInfo.controllerCreateImprintUser) : taskInfo.controllerCreateImprintUser != null)
            return false;
        if (removeOldDataFromStorage != null ? !removeOldDataFromStorage.equals(taskInfo.removeOldDataFromStorage) : taskInfo.removeOldDataFromStorage != null)
            return false;
        if (fetchFollower != null ? !fetchFollower.equals(taskInfo.fetchFollower) : taskInfo.fetchFollower != null)
            return false;
        if (fetchFriends != null ? !fetchFriends.equals(taskInfo.fetchFriends) : taskInfo.fetchFriends != null)
            return false;
        if (getHomeTimeline != null ? !getHomeTimeline.equals(taskInfo.getHomeTimeline) : taskInfo.getHomeTimeline != null)
            return false;
        if (getUserTimeline != null ? !getUserTimeline.equals(taskInfo.getUserTimeline) : taskInfo.getUserTimeline != null)
            return false;
        if (getMentions != null ? !getMentions.equals(taskInfo.getMentions) : taskInfo.getMentions != null)
            return false;
        if (getFavorites != null ? !getFavorites.equals(taskInfo.getFavorites) : taskInfo.getFavorites != null)
            return false;
        if (getRetweetsOfMe != null ? !getRetweetsOfMe.equals(taskInfo.getRetweetsOfMe) : taskInfo.getRetweetsOfMe != null)
            return false;
        if (getLists != null ? !getLists.equals(taskInfo.getLists) : taskInfo.getLists != null) return false;
        if (fetchUserlistOwners != null ? !fetchUserlistOwners.equals(taskInfo.fetchUserlistOwners) : taskInfo.fetchUserlistOwners != null)
            return false;
        return startGarbageCollection != null ? startGarbageCollection.equals(taskInfo.startGarbageCollection) : taskInfo.startGarbageCollection == null;
    }

    @Override
    public int hashCode() {
        int result = fetchTweetsFromSearch != null ? fetchTweetsFromSearch.hashCode() : 0;
        result = 31 * result + (updateTweets != null ? updateTweets.hashCode() : 0);
        result = 31 * result + (updatedUsers != null ? updatedUsers.hashCode() : 0);
        result = 31 * result + (updateUsersFromMentions != null ? updateUsersFromMentions.hashCode() : 0);
        result = 31 * result + (fetchUsersFromList != null ? fetchUsersFromList.hashCode() : 0);
        result = 31 * result + (controllerCreateTestdataTweets != null ? controllerCreateTestdataTweets.hashCode() : 0);
        result = 31 * result + (controllerCreateTestdataUsers != null ? controllerCreateTestdataUsers.hashCode() : 0);
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
        return "TaskInfo{" +
                "fetchTweetsFromSearch=" + fetchTweetsFromSearch +
                ", updateTweets=" + updateTweets +
                ", updatedUsers=" + updatedUsers +
                ", updateUsersFromMentions=" + updateUsersFromMentions +
                ", fetchUsersFromList=" + fetchUsersFromList +
                ", controllerCreateTestdataTweets=" + controllerCreateTestdataTweets +
                ", controllerCreateTestdataUsers=" + controllerCreateTestdataUsers +
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

    @Transient
    public String getUniqueId() {
        return toString();
    }
}
