package org.woehlke.twitterwall.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 19.06.17.
 */
@Service
public class ScheduledTasksFacadeImpl implements ScheduledTasksFacade {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksFacadeImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${twitter.consumerKey}")
    private String twitterConsumerKey;

    @Value("${twitter.consumerSecret}")
    private String twitterConsumerSecret;

    @Value("${twitter.accessToken}")
    private String twitterAccessToken;

    @Value("${twitter.accessTokenSecret}")
    private String twitterAccessTokenSecret;

    @Value("${twitter.pageSize}")
    private int pageSize = 100;

    @Value("${twitter.searchQuery}")
    private String searchQuery;

    @Autowired
    public ScheduledTasksFacadeImpl(StoreTweetsProcess storeTweetsProcess) {
        this.storeTweetsProcess = storeTweetsProcess;
    }

    private final StoreTweetsProcess storeTweetsProcess;

    private Twitter getTwitterProxy(){
        Twitter twitter = new TwitterTemplate(
                twitterConsumerKey,
                twitterConsumerSecret,
                twitterAccessToken,
                twitterAccessTokenSecret);
        return twitter;
    }

    @Override
    public void fetchTweetsFromTwitterSearch() {
        log.info("fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        Twitter twitter = getTwitterProxy();
        try {
            List<Tweet> tweets = twitter.searchOperations().search(searchQuery, pageSize).getTweets();
            log.info("---------------------------------------");
            for (Tweet tweet : tweets) {
                log.info("User: @" + tweet.getFromUser());
                log.info("Text:  " + tweet.getText());
                log.info("Image: " + tweet.getProfileImageUrl());
                this.storeTweetsProcess.storeOneTweet(tweet);
                log.info("---------------------------------------");
            }
        } catch (ResourceAccessException e){
            log.error("Twitter: "+e.getMessage());
            log.error("Twitter: check your Network Connection!");
        }
    }

    @Override
    public void fetchFollowersFromTwitter() {
        log.info("fetchFollowersFromTwitter: The time is now {}", dateFormat.format(new Date()));
        Twitter twitter = getTwitterProxy();
        try {
            Thread.sleep(20000);
            CursoredList<TwitterProfile> followers = twitter.friendOperations().getFollowers();
            boolean run = true;
            while(run) {
                for(TwitterProfile follower: followers){
                    storeTweetsProcess.storeFollower(follower);
                }
                if(followers.hasNext()){
                    long cursor = followers.getNextCursor();
                    followers = twitter.friendOperations().getFriendsInCursor(cursor);
                } else {
                    run = false;
                }
                Thread.sleep(600000);
            }
        } catch (ResourceAccessException e){
            log.error("Twitter: "+e.getMessage());
            log.error("Twitter: check your Network Connection!");
        } catch (RateLimitExceededException e){
            log.warn("Twitter: "+e.getMessage());
        } catch (InterruptedException e) {
            log.warn("InterruptedException: "+e.getMessage());
        }
    }

    @Override
    public void fetchFriendsFromTwitter() {
        log.info("fetchFollowersFromTwitter: The time is now {}", dateFormat.format(new Date()));
        Twitter twitter = getTwitterProxy();
        try {
            Thread.sleep(20000);
            CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
            boolean run = true;
            while (run) {
                for (TwitterProfile friend : friends) {
                    storeTweetsProcess.storeFriend(friend);
                }
                if (friends.hasNext()) {
                    long cursor = friends.getNextCursor();
                    friends = twitter.friendOperations().getFriendsInCursor(cursor);
                } else {
                    run = false;
                }
                Thread.sleep(600000);
            }
        } catch (ResourceAccessException e){
            log.error("Twitter: "+e.getMessage());
            log.error("Twitter: check your Network Connection!");
        } catch (RateLimitExceededException e){
            log.error("Twitter: "+e.getMessage());
        } catch (InterruptedException e) {
            log.error("InterruptedException: "+e.getMessage());
        }
    }
}
