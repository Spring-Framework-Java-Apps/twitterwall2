package org.woehlke.twitterwall.scheduled.service.facade;

import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;

/**
 * Created by tw on 18.07.17.
 */
public interface CreateTestData {

    org.springframework.data.domain.Page<Tweet> getTestDataTweets();

    org.springframework.data.domain.Page<org.woehlke.twitterwall.oodm.entities.User> getTestDataUser();

    User addUserForScreenName(String screenName);

}
