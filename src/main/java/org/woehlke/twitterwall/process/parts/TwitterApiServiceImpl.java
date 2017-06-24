package org.woehlke.twitterwall.process.parts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.woehlke.twitterwall.process.parts.TwitterApiService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
//@javax.transaction.Transactional(javax.transaction.Transactional.TxType.NOT_SUPPORTED)
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TwitterApiServiceImpl implements TwitterApiService {

    @Value("${twitter.consumerKey}")
    private String twitterConsumerKey;

    @Value("${twitter.consumerSecret}")
    private String twitterConsumerSecret;

    @Value("${twitter.accessToken}")
    private String twitterAccessToken;

    @Value("${twitter.accessTokenSecret}")
    private String twitterAccessTokenSecret;

    @Value("${twitter.pageSize}")
    private int pageSize;

    @Value("${twitterwall.twitter.search.sinceId}")
    private long sinceId;

    @Value("${twitterwall.twitter.search.maxId}")
    private long maxId;

    @Value("${twitter.searchQuery}")
    private String searchQuery;

    private Twitter getTwitterProxy() {
        Twitter twitter = new TwitterTemplate(
                twitterConsumerKey,
                twitterConsumerSecret,
                twitterAccessToken,
                twitterAccessTokenSecret);
        return twitter;
    }

    @Override
    public List<Tweet> findTweetsForSearchQuery() {
        return getTwitterProxy().searchOperations().search(searchQuery, pageSize).getTweets();
    }

    @Override
    public List<Tweet> findTweetsForSearchQueryStartingWith(long id) {
        return getTwitterProxy().searchOperations().search(searchQuery, pageSize, id, Long.MAX_VALUE).getTweets();
    }

    @Override
    public Tweet findOneTweetById(long id) {
        return getTwitterProxy().timelineOperations().getStatus(id);
    }

    @Override
    public List<TwitterProfile> getFollowers() {
        List<TwitterProfile> list = new ArrayList<TwitterProfile>();
        CursoredList<TwitterProfile> followers = getTwitterProxy().friendOperations().getFollowers();
        boolean run = true;
        while (run) {
            for (TwitterProfile follower : followers) {
                list.add(follower);
            }
            if (followers.hasNext()) {
                long cursor = followers.getNextCursor();
                followers = getTwitterProxy().friendOperations().getFriendsInCursor(cursor);
            } else {
                run = false;
            }
        }
        return list;
    }

    @Override
    public List<TwitterProfile> getFriends() {
        List<TwitterProfile> list = new ArrayList<TwitterProfile>();
        try {
            CursoredList<TwitterProfile> friends = getTwitterProxy().friendOperations().getFriends();
            boolean run = true;
            while (run) {
                for (TwitterProfile friend : friends) {
                    list.add(friend);
                }
                if (friends.hasNext()) {
                    long cursor = friends.getNextCursor();
                    friends = getTwitterProxy().friendOperations().getFriendsInCursor(cursor);
                } else {
                    run = false;
                }
            }
        } catch (RuntimeException rte) {

        } catch (Exception e) {

        }
        return list;
    }

    @Override
    public TwitterProfile getUserProfileForTwitterId(long userProfileTwitterId) {
        return getTwitterProxy().userOperations().getUserProfile(userProfileTwitterId);
    }
}
