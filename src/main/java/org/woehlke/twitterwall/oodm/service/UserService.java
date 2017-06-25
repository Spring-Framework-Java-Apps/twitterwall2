package org.woehlke.twitterwall.oodm.service;

import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.common.OodmService;

import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
public interface UserService extends OodmService {

    User findByIdTwitter(long idTwitter);

    User persist(User user);

    User update(User user);

    List<User> getAll();

    User findByScreenName(String screenName);

    List<User> getTweetingUsers();

    List<User> getNotYetFriendUsers();

    List<String> getAllDescriptions();

    List<Long> getAllTwitterIds();

    User storeUser(User user);

    User storeUserProcess(User user);

    User transform(TwitterProfile twitterProfile);

    User getEntitiesForUrlDescription(User user);

    User storeUserProfile(TwitterProfile userProfile);
}
