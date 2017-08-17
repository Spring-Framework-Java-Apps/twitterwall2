package org.woehlke.twitterwall.oodm.entities.parts;

import org.springframework.validation.annotation.Validated;
import org.woehlke.twitterwall.oodm.entities.Task;

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
    @Column(nullable = false,name="controller_add_user_for_screen_name")
    private Boolean controllerAddUserForScreenName = false;

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
                case UPDATE_USERS_FROM_MENTIONS:
                    this.updateUsersFromMentions = true;
                    break;
                case FETCH_USERS_FROM_LIST:
                    this.fetchUsersFromList = true;
                    break;
                case CONTROLLER_ADD_USER_FOR_SCREEN_NAME:
                    this.controllerAddUserForScreenName = true;
                    break;
                case CONTROLLER_CREATE_TESTDATA_TWEETS:
                    this.controllerCreateTestdataTweets = true;
                    break;
                case CONTROLLER_CREATE_TESTDATA_USERS:
                    this.controllerCreateTestdataUsers = true;
                    break;
                case CONTROLLER_CREATE_IMPRINT_USER:
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
                default:
                    break;
            }
        }
    }

    protected TaskInfo() {
    }

    public TaskInfo(Boolean fetchTweetsFromSearch, Boolean updateTweets, Boolean updatedUsers, Boolean updateUsersFromMentions, Boolean fetchUsersFromList, Boolean controllerAddUserForScreenName, Boolean controllerCreateTestdataTweets, Boolean controllerCreateTestdataUsers, Boolean controllerCreateImprintUser, Boolean removeOldDataFromStorage, Boolean fetchFollower, Boolean fetchFriends) {
        this.fetchTweetsFromSearch = fetchTweetsFromSearch;
        this.updateTweets = updateTweets;
        this.updatedUsers = updatedUsers;
        this.updateUsersFromMentions = updateUsersFromMentions;
        this.fetchUsersFromList = fetchUsersFromList;
        this.controllerAddUserForScreenName = controllerAddUserForScreenName;
        this.controllerCreateTestdataTweets = controllerCreateTestdataTweets;
        this.controllerCreateTestdataUsers = controllerCreateTestdataUsers;
        this.controllerCreateImprintUser = controllerCreateImprintUser;
        this.removeOldDataFromStorage = removeOldDataFromStorage;
        this.fetchFollower = fetchFollower;
        this.fetchFriends = fetchFriends;
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

    public Boolean getControllerAddUserForScreenName() {
        return controllerAddUserForScreenName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskInfo)) return false;

        TaskInfo taskInfo = (TaskInfo) o;

        if (getFetchTweetsFromSearch() != null ? !getFetchTweetsFromSearch().equals(taskInfo.getFetchTweetsFromSearch()) : taskInfo.getFetchTweetsFromSearch() != null)
            return false;
        if (getUpdateTweets() != null ? !getUpdateTweets().equals(taskInfo.getUpdateTweets()) : taskInfo.getUpdateTweets() != null)
            return false;
        if (getUpdatedUsers() != null ? !getUpdatedUsers().equals(taskInfo.getUpdatedUsers()) : taskInfo.getUpdatedUsers() != null)
            return false;
        if (getUpdateUsersFromMentions() != null ? !getUpdateUsersFromMentions().equals(taskInfo.getUpdateUsersFromMentions()) : taskInfo.getUpdateUsersFromMentions() != null)
            return false;
        if (getFetchUsersFromList() != null ? !getFetchUsersFromList().equals(taskInfo.getFetchUsersFromList()) : taskInfo.getFetchUsersFromList() != null)
            return false;
        if (getControllerAddUserForScreenName() != null ? !getControllerAddUserForScreenName().equals(taskInfo.getControllerAddUserForScreenName()) : taskInfo.getControllerAddUserForScreenName() != null)
            return false;
        if (getControllerCreateTestdataTweets() != null ? !getControllerCreateTestdataTweets().equals(taskInfo.getControllerCreateTestdataTweets()) : taskInfo.getControllerCreateTestdataTweets() != null)
            return false;
        if (getControllerCreateTestdataUsers() != null ? !getControllerCreateTestdataUsers().equals(taskInfo.getControllerCreateTestdataUsers()) : taskInfo.getControllerCreateTestdataUsers() != null)
            return false;
        if (getControllerCreateImprintUser() != null ? !getControllerCreateImprintUser().equals(taskInfo.getControllerCreateImprintUser()) : taskInfo.getControllerCreateImprintUser() != null)
            return false;
        if (getRemoveOldDataFromStorage() != null ? !getRemoveOldDataFromStorage().equals(taskInfo.getRemoveOldDataFromStorage()) : taskInfo.getRemoveOldDataFromStorage() != null)
            return false;
        if (getFetchFollower() != null ? !getFetchFollower().equals(taskInfo.getFetchFollower()) : taskInfo.getFetchFollower() != null)
            return false;
        if (getFetchFriends() != null ? !getFetchFriends().equals(taskInfo.getFetchFriends()) : taskInfo.getFetchFriends() != null)
            return false;
        if (getGetHomeTimeline() != null ? !getGetHomeTimeline().equals(taskInfo.getGetHomeTimeline()) : taskInfo.getGetHomeTimeline() != null)
            return false;
        if (getGetUserTimeline() != null ? !getGetUserTimeline().equals(taskInfo.getGetUserTimeline()) : taskInfo.getGetUserTimeline() != null)
            return false;
        if (getGetMentions() != null ? !getGetMentions().equals(taskInfo.getGetMentions()) : taskInfo.getGetMentions() != null)
            return false;
        if (getGetFavorites() != null ? !getGetFavorites().equals(taskInfo.getGetFavorites()) : taskInfo.getGetFavorites() != null)
            return false;
        if (getGetRetweetsOfMe() != null ? !getGetRetweetsOfMe().equals(taskInfo.getGetRetweetsOfMe()) : taskInfo.getGetRetweetsOfMe() != null)
            return false;
        return getGetLists() != null ? getGetLists().equals(taskInfo.getGetLists()) : taskInfo.getGetLists() == null;
    }

    @Override
    public int hashCode() {
        int result = getFetchTweetsFromSearch() != null ? getFetchTweetsFromSearch().hashCode() : 0;
        result = 31 * result + (getUpdateTweets() != null ? getUpdateTweets().hashCode() : 0);
        result = 31 * result + (getUpdatedUsers() != null ? getUpdatedUsers().hashCode() : 0);
        result = 31 * result + (getUpdateUsersFromMentions() != null ? getUpdateUsersFromMentions().hashCode() : 0);
        result = 31 * result + (getFetchUsersFromList() != null ? getFetchUsersFromList().hashCode() : 0);
        result = 31 * result + (getControllerAddUserForScreenName() != null ? getControllerAddUserForScreenName().hashCode() : 0);
        result = 31 * result + (getControllerCreateTestdataTweets() != null ? getControllerCreateTestdataTweets().hashCode() : 0);
        result = 31 * result + (getControllerCreateTestdataUsers() != null ? getControllerCreateTestdataUsers().hashCode() : 0);
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
        return "TaskInfo{" +
            "fetchTweetsFromSearch=" + fetchTweetsFromSearch +
            ", updateTweets=" + updateTweets +
            ", updatedUsers=" + updatedUsers +
            ", updateUsersFromMentions=" + updateUsersFromMentions +
            ", fetchUsersFromList=" + fetchUsersFromList +
            ", controllerAddUserForScreenName=" + controllerAddUserForScreenName +
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
            '}';
    }

    @Transient
    public String getUniqueId() {
        return toString();
    }
}
