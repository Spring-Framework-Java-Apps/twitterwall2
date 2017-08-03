package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.woehlke.twitterwall.oodm.entities.Task;

public interface AsyncStartTask {

    Task updateTweets();

    Task updateUserProfiles();

    Task updateUserProfilesFromMentions();

    Task fetchTweetsFromTwitterSearch();

    Task fetchUsersFromDefinedUserList();

}
