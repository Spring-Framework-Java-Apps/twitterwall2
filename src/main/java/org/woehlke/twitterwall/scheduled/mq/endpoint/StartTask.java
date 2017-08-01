package org.woehlke.twitterwall.scheduled.mq.endpoint;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;

import java.util.List;

public interface StartTask extends AsyncStartTask {

    User createImprintUser();

    List<User> createTestDataForUser();

    List<Tweet> createTestDataForTweets();

}
