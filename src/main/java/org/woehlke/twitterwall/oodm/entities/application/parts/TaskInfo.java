package org.woehlke.twitterwall.oodm.entities.application.parts;

import org.woehlke.twitterwall.oodm.entities.application.Task;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by tw on 10.07.17.
 */
@Embeddable
public class TaskInfo implements Serializable {

    private Boolean updatedByFetchTweetsFromTwitterSearch = false;
    private Boolean updatedByUpdateTweets  = false;
    private Boolean updatedByUpdateUserProfiles  = false;
    private Boolean updatedByUpdateUserProfilesFromMentions  = false;
    private Boolean updatedByFetchUsersFromDefinedUserList  = false;

    private Boolean controllerAddUserForScreenName = false;
    private Boolean controllerGetTestdataForTweets = false;
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
