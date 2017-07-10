package org.woehlke.twitterwall.oodm.entities.application;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by tw on 10.07.17.
 */
@Embeddable
public class TaskInfo implements Serializable {

    private boolean updatedByFetchTweetsFromTwitterSearch = false;
    private boolean updatedByUpdateTweets  = false;
    private boolean updatedByUpdateUserProfiles  = false;
    private boolean updatedByUpdateUserProfilesFromMentions  = false;
    private boolean updatedByFetchUsersFromDefinedUserList  = false;

    public TaskInfo() {
    }

    public boolean isUpdatedByFetchTweetsFromTwitterSearch() {
        return updatedByFetchTweetsFromTwitterSearch;
    }

    public void setUpdatedByFetchTweetsFromTwitterSearch(boolean updatedByFetchTweetsFromTwitterSearch) {
        this.updatedByFetchTweetsFromTwitterSearch |= updatedByFetchTweetsFromTwitterSearch;
    }

    public boolean isUpdatedByUpdateTweets() {
        return updatedByUpdateTweets;
    }

    public void setUpdatedByUpdateTweets(boolean updatedByUpdateTweets) {
        this.updatedByUpdateTweets |= updatedByUpdateTweets;
    }

    public boolean isUpdatedByUpdateUserProfiles() {
        return updatedByUpdateUserProfiles;
    }

    public void setUpdatedByUpdateUserProfiles(boolean updatedByUpdateUserProfiles) {
        this.updatedByUpdateUserProfiles |= updatedByUpdateUserProfiles;
    }

    public boolean isUpdatedByUpdateUserProfilesFromMentions() {
        return updatedByUpdateUserProfilesFromMentions;
    }

    public void setUpdatedByUpdateUserProfilesFromMentions(boolean updatedByUpdateUserProfilesFromMentions) {
        this.updatedByUpdateUserProfilesFromMentions |= updatedByUpdateUserProfilesFromMentions;
    }

    public boolean isUpdatedByFetchUsersFromDefinedUserList() {
        return updatedByFetchUsersFromDefinedUserList;
    }

    public void setUpdatedByFetchUsersFromDefinedUserList(boolean updatedByFetchUsersFromDefinedUserList) {
        this.updatedByFetchUsersFromDefinedUserList |= updatedByFetchUsersFromDefinedUserList;
    }

}
