package org.woehlke.twitterwall.oodm.entities.parts;

import org.woehlke.twitterwall.oodm.entities.Task;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by tw on 10.07.17.
 */
@Embeddable
public class TaskInfo implements Serializable {

    @NotNull
    @Column(nullable = false,name="scheduler_fetch_tweets_from_twitter_search")
    private Boolean updatedByFetchTweetsFromTwitterSearch = false;

    @NotNull
    @Column(nullable = false,name="scheduler_update_tweets")
    private Boolean updatedByUpdateTweets  = false;

    @NotNull
    @Column(nullable = false,name="scheduler_update_users")
    private Boolean updatedByUpdateUserProfiles  = false;

    @NotNull
    @Column(nullable = false,name="scheduler_update_users_from_mentions")
    private Boolean updatedByUpdateUserProfilesFromMentions  = false;

    @NotNull
    @Column(nullable = false,name="scheduler_fetch_users_from_defined_user_list")
    private Boolean updatedByFetchUsersFromDefinedUserList  = false;

    @NotNull
    @Column(nullable = false,name="controller_add_user_for_screen_name")
    private Boolean controllerAddUserForScreenName = false;

    @NotNull
    @Column(nullable = false,name="controller_get_testdata_for_tweets")
    private Boolean controllerGetTestdataForTweets = false;

    @NotNull
    @Column(nullable = false,name="controller_get_testdata_for_users")
    private Boolean controllerGetTestdataForUsers = false;

    public TaskInfo() {
    }

    public TaskInfo(Boolean updatedByFetchTweetsFromTwitterSearch, Boolean updatedByUpdateTweets, Boolean updatedByUpdateUserProfiles, Boolean updatedByUpdateUserProfilesFromMentions, Boolean updatedByFetchUsersFromDefinedUserList, Boolean controllerAddUserForScreenName, Boolean controllerGetTestdataForTweets, Boolean controllerGetTestdataForUsers) {
        this.updatedByFetchTweetsFromTwitterSearch = updatedByFetchTweetsFromTwitterSearch;
        this.updatedByUpdateTweets = updatedByUpdateTweets;
        this.updatedByUpdateUserProfiles = updatedByUpdateUserProfiles;
        this.updatedByUpdateUserProfilesFromMentions = updatedByUpdateUserProfilesFromMentions;
        this.updatedByFetchUsersFromDefinedUserList = updatedByFetchUsersFromDefinedUserList;
        this.controllerAddUserForScreenName = controllerAddUserForScreenName;
        this.controllerGetTestdataForTweets = controllerGetTestdataForTweets;
        this.controllerGetTestdataForUsers = controllerGetTestdataForUsers;
    }

    public Boolean isUpdatedByFetchTweetsFromTwitterSearch() {
        return updatedByFetchTweetsFromTwitterSearch;
    }

    public void setUpdatedByFetchTweetsFromTwitterSearch(Boolean updatedByFetchTweetsFromTwitterSearch) {
        this.updatedByFetchTweetsFromTwitterSearch |= updatedByFetchTweetsFromTwitterSearch;
    }

    public Boolean isUpdatedByUpdateTweets() {
        return updatedByUpdateTweets;
    }

    public void setUpdatedByUpdateTweets(Boolean updatedByUpdateTweets) {
        this.updatedByUpdateTweets |= updatedByUpdateTweets;
    }

    public Boolean isUpdatedByUpdateUserProfiles() {
        return updatedByUpdateUserProfiles;
    }

    public void setUpdatedByUpdateUserProfiles(Boolean updatedByUpdateUserProfiles) {
        this.updatedByUpdateUserProfiles |= updatedByUpdateUserProfiles;
    }

    public Boolean isUpdatedByUpdateUserProfilesFromMentions() {
        return updatedByUpdateUserProfilesFromMentions;
    }

    public void setUpdatedByUpdateUserProfilesFromMentions(Boolean updatedByUpdateUserProfilesFromMentions) {
        this.updatedByUpdateUserProfilesFromMentions |= updatedByUpdateUserProfilesFromMentions;
    }

    public Boolean isUpdatedByFetchUsersFromDefinedUserList() {
        return updatedByFetchUsersFromDefinedUserList;
    }

    public void setUpdatedByFetchUsersFromDefinedUserList(Boolean updatedByFetchUsersFromDefinedUserList) {
        this.updatedByFetchUsersFromDefinedUserList |= updatedByFetchUsersFromDefinedUserList;
    }

    public Boolean getUpdatedByFetchTweetsFromTwitterSearch() {
        return updatedByFetchTweetsFromTwitterSearch;
    }

    public Boolean getUpdatedByUpdateTweets() {
        return updatedByUpdateTweets;
    }

    public Boolean getUpdatedByUpdateUserProfiles() {
        return updatedByUpdateUserProfiles;
    }

    public Boolean getUpdatedByUpdateUserProfilesFromMentions() {
        return updatedByUpdateUserProfilesFromMentions;
    }

    public Boolean getUpdatedByFetchUsersFromDefinedUserList() {
        return updatedByFetchUsersFromDefinedUserList;
    }

    public Boolean getControllerAddUserForScreenName() {
        return controllerAddUserForScreenName;
    }

    public void setControllerAddUserForScreenName(Boolean controllerAddUserForScreenName) {
        this.controllerAddUserForScreenName |= controllerAddUserForScreenName;
    }

    public Boolean getControllerGetTestdataForTweets() {
        return controllerGetTestdataForTweets;
    }

    public void setControllerGetTestdataForTweets(Boolean controllerGetTestdataForTweets) {
        this.controllerGetTestdataForTweets |= controllerGetTestdataForTweets;
    }

    public Boolean getControllerGetTestdataForUsers() {
        return controllerGetTestdataForUsers;
    }

    public void setControllerGetTestdataForUsers(Boolean controllerGetTestdataForUsers) {
        this.controllerGetTestdataForUsers |= controllerGetTestdataForUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskInfo)) return false;

        TaskInfo taskInfo = (TaskInfo) o;

        if (updatedByFetchTweetsFromTwitterSearch != null ? !updatedByFetchTweetsFromTwitterSearch.equals(taskInfo.updatedByFetchTweetsFromTwitterSearch) : taskInfo.updatedByFetchTweetsFromTwitterSearch != null)
            return false;
        if (updatedByUpdateTweets != null ? !updatedByUpdateTweets.equals(taskInfo.updatedByUpdateTweets) : taskInfo.updatedByUpdateTweets != null)
            return false;
        if (updatedByUpdateUserProfiles != null ? !updatedByUpdateUserProfiles.equals(taskInfo.updatedByUpdateUserProfiles) : taskInfo.updatedByUpdateUserProfiles != null)
            return false;
        if (updatedByUpdateUserProfilesFromMentions != null ? !updatedByUpdateUserProfilesFromMentions.equals(taskInfo.updatedByUpdateUserProfilesFromMentions) : taskInfo.updatedByUpdateUserProfilesFromMentions != null)
            return false;
        if (updatedByFetchUsersFromDefinedUserList != null ? !updatedByFetchUsersFromDefinedUserList.equals(taskInfo.updatedByFetchUsersFromDefinedUserList) : taskInfo.updatedByFetchUsersFromDefinedUserList != null)
            return false;
        if (controllerAddUserForScreenName != null ? !controllerAddUserForScreenName.equals(taskInfo.controllerAddUserForScreenName) : taskInfo.controllerAddUserForScreenName != null)
            return false;
        if (controllerGetTestdataForTweets != null ? !controllerGetTestdataForTweets.equals(taskInfo.controllerGetTestdataForTweets) : taskInfo.controllerGetTestdataForTweets != null)
            return false;
        return controllerGetTestdataForUsers != null ? controllerGetTestdataForUsers.equals(taskInfo.controllerGetTestdataForUsers) : taskInfo.controllerGetTestdataForUsers == null;
    }

    @Override
    public int hashCode() {
        int result = updatedByFetchTweetsFromTwitterSearch != null ? updatedByFetchTweetsFromTwitterSearch.hashCode() : 0;
        result = 31 * result + (updatedByUpdateTweets != null ? updatedByUpdateTweets.hashCode() : 0);
        result = 31 * result + (updatedByUpdateUserProfiles != null ? updatedByUpdateUserProfiles.hashCode() : 0);
        result = 31 * result + (updatedByUpdateUserProfilesFromMentions != null ? updatedByUpdateUserProfilesFromMentions.hashCode() : 0);
        result = 31 * result + (updatedByFetchUsersFromDefinedUserList != null ? updatedByFetchUsersFromDefinedUserList.hashCode() : 0);
        return result;
    }

    public TaskInfo setTaskInfoFromTask(Task task) {
        TaskType useCase = task.getTaskType();
        switch (useCase){
            case FETCH_TWEETS_FROM_TWITTER_SEARCH:
                this.updatedByFetchTweetsFromTwitterSearch = true;
                break;
            case UPDATE_TWEETS:
                this.updatedByUpdateTweets = true;
                break;
            case UPDATE_USER_PROFILES:
                this.updatedByUpdateUserProfiles = true;
                break;
            case UPDATE_USER_PROFILES_FROM_MENTIONS:
                this.updatedByUpdateUserProfilesFromMentions = true;
                break;
            case FETCH_USERS_FROM_DEFINED_USER_LIST:
                this.updatedByFetchUsersFromDefinedUserList = true;
                break;
            case CONTROLLER_ADD_USER_FOR_SCREEN_NAME:
                this.controllerAddUserForScreenName = true;
                break;
            case CONTROLLER_GET_TESTDATA_TWEETS:
                this.controllerGetTestdataForTweets = true;
                break;
            case CONTROLLER_GET_TESTDATA_USER:
                this.controllerGetTestdataForUsers = true;
                break;
                default: break;
        }
        return this;
    }
}
