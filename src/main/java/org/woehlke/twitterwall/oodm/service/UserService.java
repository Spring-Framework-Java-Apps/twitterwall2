package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithIdTwitter;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithScreenName;
import org.woehlke.twitterwall.oodm.service.common.DomainServiceWithTask;

import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
public interface UserService extends DomainServiceWithIdTwitter<User>,DomainServiceWithScreenName<User>,DomainServiceWithTask<User> {

    List<User> getTweetingUsers();

    List<User> getNotYetFriendUsers();

    List<User> getNotYetOnList();

    List<User> getOnList();

    List<String> getAllDescriptions();

    List<Long> getAllTwitterIds();

    List<User> getUsersForHashTag(String hashtagText);

    long countUsersForHashTag(String hashtagText);

}
