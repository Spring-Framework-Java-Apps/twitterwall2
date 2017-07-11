package org.woehlke.twitterwall.oodm.entities.application.parts;

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

    public TaskInfo() {
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

    @Override
    public String toString() {
        return "TaskInfo{" +
            "updatedByFetchTweetsFromTwitterSearch=" + updatedByFetchTweetsFromTwitterSearch +
            ", updatedByUpdateTweets=" + updatedByUpdateTweets +
            ", updatedByUpdateUserProfiles=" + updatedByUpdateUserProfiles +
            ", updatedByUpdateUserProfilesFromMentions=" + updatedByUpdateUserProfilesFromMentions +
            ", updatedByFetchUsersFromDefinedUserList=" + updatedByFetchUsersFromDefinedUserList +
            '}';
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
        return updatedByFetchUsersFromDefinedUserList != null ? updatedByFetchUsersFromDefinedUserList.equals(taskInfo.updatedByFetchUsersFromDefinedUserList) : taskInfo.updatedByFetchUsersFromDefinedUserList == null;
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
}
