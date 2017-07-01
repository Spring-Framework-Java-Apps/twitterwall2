package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithIdTwitter;
import org.woehlke.twitterwall.oodm.repository.common.DomainRepositoryWithScreenName;

import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
public interface UserRepository extends DomainRepositoryWithIdTwitter<User>,DomainRepositoryWithScreenName<User> {

    List<User> getTweetingUsers();

    List<User> getNotYetFriendUsers();

    List<String> getAllDescriptions();

    List<Long> getAllTwitterIds();

    List<User> getUsersForHashTag(String hashtagText);

    long countUsersForHashTag(String hashtagText);
    
}
