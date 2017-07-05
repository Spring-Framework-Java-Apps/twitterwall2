package org.woehlke.twitterwall.scheduled;

import org.woehlke.twitterwall.frontend.model.CountedEntities;

/**
 * Created by tw on 19.06.17.
 */
public interface ScheduledTasksFacade {

    CountedEntities fetchTweetsFromTwitterSearch();

    CountedEntities updateUserProfiles();

    CountedEntities updateUserProfilesFromMentions();

    CountedEntities updateTweets();

    void fetchUsersFromDefinedUserList();

}
