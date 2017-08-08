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
                default:
                    break;
            }
        }
    }

    protected TaskInfo() {
    }

    public TaskInfo(Boolean fetchTweetsFromSearch, Boolean updateTweets, Boolean updatedUsers, Boolean updateUsersFromMentions, Boolean fetchUsersFromList, Boolean controllerAddUserForScreenName, Boolean controllerCreateTestdataTweets, Boolean controllerCreateTestdataUsers,Boolean controllerCreateImprintUser,Boolean removeOldDataFromStorage,Boolean fetchFollower ) {
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
        if (controllerAddUserForScreenName != null ? !controllerAddUserForScreenName.equals(taskInfo.controllerAddUserForScreenName) : taskInfo.controllerAddUserForScreenName != null)
            return false;
        if (controllerCreateTestdataTweets != null ? !controllerCreateTestdataTweets.equals(taskInfo.controllerCreateTestdataTweets) : taskInfo.controllerCreateTestdataTweets != null)
            return false;
        if (controllerCreateTestdataUsers != null ? !controllerCreateTestdataUsers.equals(taskInfo.controllerCreateTestdataUsers) : taskInfo.controllerCreateTestdataUsers != null)
            return false;
        if (controllerCreateImprintUser != null ? !controllerCreateImprintUser.equals(taskInfo.controllerCreateImprintUser) : taskInfo.controllerCreateImprintUser != null)
            return false;
        if (removeOldDataFromStorage != null ? !removeOldDataFromStorage.equals(taskInfo.removeOldDataFromStorage) : taskInfo.removeOldDataFromStorage != null)
            return false;
        return fetchFollower != null ? fetchFollower.equals(taskInfo.fetchFollower) : taskInfo.fetchFollower == null;
    }

    @Override
    public int hashCode() {
        int result = fetchTweetsFromSearch != null ? fetchTweetsFromSearch.hashCode() : 0;
        result = 31 * result + (updateTweets != null ? updateTweets.hashCode() : 0);
        result = 31 * result + (updatedUsers != null ? updatedUsers.hashCode() : 0);
        result = 31 * result + (updateUsersFromMentions != null ? updateUsersFromMentions.hashCode() : 0);
        result = 31 * result + (fetchUsersFromList != null ? fetchUsersFromList.hashCode() : 0);
        result = 31 * result + (controllerAddUserForScreenName != null ? controllerAddUserForScreenName.hashCode() : 0);
        result = 31 * result + (controllerCreateTestdataTweets != null ? controllerCreateTestdataTweets.hashCode() : 0);
        result = 31 * result + (controllerCreateTestdataUsers != null ? controllerCreateTestdataUsers.hashCode() : 0);
        result = 31 * result + (controllerCreateImprintUser != null ? controllerCreateImprintUser.hashCode() : 0);
        result = 31 * result + (removeOldDataFromStorage != null ? removeOldDataFromStorage.hashCode() : 0);
        result = 31 * result + (fetchFollower != null ? fetchFollower.hashCode() : 0);
        return result;
    }

}
