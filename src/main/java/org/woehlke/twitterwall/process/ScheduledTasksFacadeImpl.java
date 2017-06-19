package org.woehlke.twitterwall.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tw on 19.06.17.
 */
@Service
@Transactional(Transactional.TxType.NOT_SUPPORTED)
public class ScheduledTasksFacadeImpl implements ScheduledTasksFacade {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksFacadeImpl.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    public ScheduledTasksFacadeImpl(StoreTweetsProcess storeTweetsProcess, TwitterApiService twitterApiService) {
        this.storeTweetsProcess = storeTweetsProcess;
        this.twitterApiService = twitterApiService;
    }

    private final StoreTweetsProcess storeTweetsProcess;

    private final TwitterApiService twitterApiService;

    @Override
    public void fetchTweetsFromTwitterSearch() {
        log.info("fetchTweetsFromTwitterSearch: The time is now {}", dateFormat.format(new Date()));
        try {
            log.info("---------------------------------------");
            int loopId = 0;
            long lastId = 0L;
            for (Tweet tweet : twitterApiService.findTweetsForSearchQuery()) {
                loopId++;
                lastId=tweet.getId();
                handleTweet(tweet,loopId);
            }
            /*
            if(lastId>0L){
               for(int i=0; i<5; i++){
                   for (Tweet tweet : twitterApiService.findTweetsForSearchQueryStartingWith(lastId)) {
                       loopId++;
                       lastId=tweet.getId();
                       handleTweet(tweet,loopId);
                   }
               }
            } */
        } catch (ResourceAccessException e){
            log.error("Twitter: "+e.getMessage());
            log.error("Twitter: check your Network Connection!");
        }
    }

    private void handleTweet(Tweet tweet, int loopId){
        log.info(""+loopId);
        log.info("Id:    " + tweet.getId());
        log.info("User: @" + tweet.getFromUser());
        log.info("Text:  " + tweet.getText());
        log.info("Image: " + tweet.getProfileImageUrl());
        this.storeTweetsProcess.storeOneTweet(tweet);
        log.info("---------------------------------------");
    }

    @Override
    public void fetchFollowersFromTwitter() {
        log.info("fetchFollowersFromTwitter: The time is now {}", dateFormat.format(new Date()));
        try {
            for(TwitterProfile follower:twitterApiService.getFollowers()){
                storeTweetsProcess.storeFollower(follower);
            }
        } catch (ResourceAccessException e){
            log.error("Twitter: "+e.getMessage());
            log.error("Twitter: check your Network Connection!");
        } catch (RateLimitExceededException e){
            log.warn("Twitter: "+e.getMessage());
        }
    }

    @Override
    public void fetchFriendsFromTwitter() {
        log.info("fetchFollowersFromTwitter: The time is now {}", dateFormat.format(new Date()));
        try {
            for(TwitterProfile follower:twitterApiService.getFriends()){
                storeTweetsProcess.storeFriend(follower);
            }
        } catch (ResourceAccessException e){
            log.error("Twitter: "+e.getMessage());
            log.error("Twitter: check your Network Connection!");
        } catch (RateLimitExceededException e){
            log.error("Twitter: "+e.getMessage());
        }
    }
}
