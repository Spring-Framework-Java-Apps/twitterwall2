package org.woehlke.twitterwall.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;
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

    @Scheduled(fixedRate = 6000000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        Twitter twitter = new TwitterTemplate(
                twitterConsumerKey,
                twitterConsumerSecret,
                twitterAccessToken,
                twitterAccessTokenSecret);
        List<Tweet> tweets = twitter.searchOperations().search(searchQuery,pageSize).getTweets();
        log.info("---------------------------------------");
        for(Tweet tweet: tweets){
            log.info(tweet.getFromUser());
            log.info(tweet.getText());
            log.info(tweet.getProfileImageUrl());
        }
        log.info("---------------------------------------");
    }
}
