package org.woehlke.twitterwall.oodm.repository;

import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
public interface MyTwitterProfileRepository {

    MyTwitterProfile findByIdTwitter(long idTwitter);

    MyTwitterProfile persist(MyTwitterProfile  myTwitterProfile);

    MyTwitterProfile update(MyTwitterProfile  myTwitterProfile);

    List<MyTwitterProfile> getFollower();

    List<MyTwitterProfile> getFriends();

    List<MyTwitterProfile> getAll();
}
