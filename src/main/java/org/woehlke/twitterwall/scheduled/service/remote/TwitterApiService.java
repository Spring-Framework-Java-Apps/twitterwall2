package org.woehlke.twitterwall.scheduled.service.remote;

import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
public interface TwitterApiService {

    List<Tweet> findTweetsForSearchQuery();

    Tweet findOneTweetById(long tweetTwitterId);

    TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId);

    TwitterProfile getUserProfileForScreenName(String screenName);

    List<TwitterProfile> findUsersFromDefinedList(String screenName,String fetchUserListName);

    CursoredList<Long> findFollower();

    CursoredList<Long> findFriends();
}
