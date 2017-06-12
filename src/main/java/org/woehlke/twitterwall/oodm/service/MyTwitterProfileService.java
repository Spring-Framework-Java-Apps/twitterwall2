package org.woehlke.twitterwall.oodm.service;

import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;

import java.util.List;

/**
 * Created by tw on 11.06.17.
 */
public interface MyTwitterProfileService {

    MyTwitterProfile findByIdTwitter(long idTwitter);

    MyTwitterProfile persist(MyTwitterProfile myTwitterProfile);

    MyTwitterProfile update(MyTwitterProfile myTwitterProfile);

    List<MyTwitterProfile> getFollower();

    List<MyTwitterProfile> getFriends();

    List<MyTwitterProfile> getAll();
}
