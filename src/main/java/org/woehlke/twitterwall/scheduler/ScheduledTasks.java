package org.woehlke.twitterwall.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
import org.woehlke.twitterwall.process.StoreTweetsProcess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

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
    public ScheduledTasks(StoreTweetsProcess storeTweetsProcess) {
        this.storeTweetsProcess = storeTweetsProcess;
    }

    private final StoreTweetsProcess storeTweetsProcess;

    @Scheduled(fixedRate = 6000000)
    public void fetchTweetsFromTwitterSearch() {
        log.info("fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        Twitter twitter = getTwitterProxy();
        List<Tweet> tweets = twitter.searchOperations().search(searchQuery,pageSize).getTweets();
        log.info("---------------------------------------");
        for(Tweet tweet: tweets){
            log.info("User: @"+tweet.getFromUser());
            log.info("Text:  "+tweet.getText());
            log.info("Image: "+tweet.getProfileImageUrl());
            this.storeTweetsProcess.storeOneTweet(tweet);
        }
        log.info("---------------------------------------");
    }

    private Twitter getTwitterProxy(){
        Twitter twitter = new TwitterTemplate(
                twitterConsumerKey,
                twitterConsumerSecret,
                twitterAccessToken,
                twitterAccessTokenSecret);
        return twitter;
    }

    @Scheduled(fixedRate = 12000000)
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
        } catch (RateLimitExceededException e){
            log.error("Twitter: "+e.getMessage());
        } catch (InterruptedException e) {
            log.error("InterruptedException: "+e.getMessage());
        }
    }

    @Scheduled(fixedRate = 12000000)
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
        } catch (RateLimitExceededException e){
            log.error("Twitter: "+e.getMessage());
        } catch (InterruptedException e) {
            log.error("InterruptedException: "+e.getMessage());
        }
    }
}
