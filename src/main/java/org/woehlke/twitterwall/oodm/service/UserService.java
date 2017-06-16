package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.User;

import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
public interface UserService {

    User findByIdTwitter(long idTwitter);

    User persist(User user);

    User update(User user);

    List<User> getFollower();

    List<User> getFriends();

    List<User> getAll();

    User findByScreenName(String screenName);

    List<User> getTweetingUsers();
}