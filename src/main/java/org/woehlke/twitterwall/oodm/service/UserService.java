package org.woehlke.twitterwall.oodm.service;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.common.DomainService;

import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
public interface UserService extends DomainService<User> {

    User findByIdTwitter(long idTwitter);
    
    User findByScreenName(String screenName);

    List<User> getTweetingUsers();

    List<User> getNotYetFriendUsers();

    List<String> getAllDescriptions();

    List<Long> getAllTwitterIds();

    User storeUserProcess(User user);

    List<User> getUsersForHashTag(String hashtagText);

    long countUsersForHashTag(String hashtagText);
}
