package org.woehlke.twitterwall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Controller
public class TimelineController {

    private static final Logger log = LoggerFactory.getLogger(TimelineController.class);

    @Value("${twitter.consumerKey}")
    private String twitterConsumerKey;

    @Value("${twitter.consumerSecret}")
    private String twitterConsumerSecret;

    @Value("${twitter.accessToken}")
    private String twitterAccessToken;

    @Value("${twitter.accessTokenSecret}")
    private String twitterAccessTokenSecret;

    private Twitter twitter = null;

    @RequestMapping("/")
    public String greeting(Model model) {
        /*
        Twitter twitter = getTwitterApi();
        TwitterProfile profile = twitter.userOperations().getUserProfile();
        model.addAttribute("profile",profile);
        List<Tweet> homeTimeline  = twitter.timelineOperations().getHomeTimeline();
        model.addAttribute("timeline",homeTimeline);
        log.info("---------------------------------------");
        for(Tweet tweet: homeTimeline){
            log.info(tweet.getFromUser());
            log.info(tweet.getText());
        }
        log.info("---------------------------------------");
        */
        return "timeline";
    }

    private Twitter getTwitterApi(){
        if(twitter == null) {
            twitter =
                    new TwitterTemplate(
                            twitterConsumerKey,
                            twitterConsumerSecret,
                            twitterAccessToken,
                            twitterAccessTokenSecret);
        }
        return twitter;
    }
}
