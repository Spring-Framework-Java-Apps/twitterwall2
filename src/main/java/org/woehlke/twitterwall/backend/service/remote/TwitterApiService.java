package org.woehlke.twitterwall.backend.service.remote;

import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.woehlke.twitterwall.oodm.model.parts.User2UserList;

import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
public interface TwitterApiService {

    List<Tweet> findTweetsForSearchQuery();

    Tweet findOneTweetById(long tweetTwitterId);

    List<Tweet> getHomeTimeline();

    List<Tweet> getUserTimeline();

    List<Tweet> getMentions();

    List<Tweet> getFavorites();

    List<Tweet> getRetweetsOfMe();

    TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId);

    TwitterProfile getUserProfileForScreenName(String screenName);

    List<TwitterProfile> findUsersFromDefinedList(String screenName,String fetchUserListName);

    CursoredList<Long> getFollowerIds();

    CursoredList<Long> getFriendIds();

    User2UserList getLists();

    User2UserList getUserListForUser(long idTwitterOfListOwningUser);
}
