package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.frontend.model.Page;
import org.woehlke.twitterwall.oodm.exceptions.remote.TwitterApiException;
import org.woehlke.twitterwall.process.tasks.PersistDataFromTwitter;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.RateLimitExceededException;

import javax.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

import static org.woehlke.twitterwall.process.tasks.ScheduledTasksFacadeTest.ID_TWITTER_TO_FETCH_FOR_TWEET_TEST;

/**
 * Created by tw on 30.06.17.
 */
@Controller
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private final TwitterApiService twitterApiService;

    private final PersistDataFromTwitter persistDataFromTwitter;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    //@Value("${twitterwall.backend.twitter.fetchTestData}")
    //private boolean fetchTestData;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Autowired
    public TestController(TwitterApiService twitterApiService, PersistDataFromTwitter persistDataFromTwitter) {
        this.twitterApiService = twitterApiService;
        this.persistDataFromTwitter = persistDataFromTwitter;
    }
    
    @RequestMapping("/getTestData")
    public String getTestData(Model model) {
        logEnv();
        model = setupPage(model);
        String msg = "getTestData URL:";
        List<org.woehlke.twitterwall.oodm.entities.Tweet> latest =  new ArrayList<>();
        /*
        if (fetchTestData) {
            try {
                List<Tweet> list = tweetService.getTestTweetsForTweetTest();
                latest.addAll(list);
            } catch (Exception e){
                log.error("getTestTweetsForTweetTest: "+e.getMessage());
            }
        }   */
        try {
                int loopId=0;
                for (long idTwitter : ID_TWITTER_TO_FETCH_FOR_TWEET_TEST) {
                    try {
                        Tweet tweet = twitterApiService.findOneTweetById(idTwitter);
                        loopId++;
                        log.info(msg+loopId);
                        org.woehlke.twitterwall.oodm.entities.Tweet persTweet = this.persistDataFromTwitter.storeOneTweet(tweet);
                        latest.add(persTweet);
                    } catch (EmptyResultDataAccessException e)  {
                        log.warn(e.getMessage());
                    } catch (TwitterApiException ex){
                        log.warn(ex.getMessage());
                    } catch (NoResultException e){
                        log.warn(e.getMessage());
                    }
                }
        } catch (RateLimitExceededException e){
            log.info(e.getMessage());
        } catch (Exception e){
            log.warn(e.getMessage());
        }
        model.addAttribute("latestTweets", latest);
        return "timeline";
    }



    private void logEnv(){
        log.info("twitterwall.frontend.theme = "+theme);
        log.info("twitterwall.frontend.info.webpage = "+infoWebpage);
        //log.info("twitterwall.backend.twitter.fetchTestData = "+fetchTestData);
        log.info("twitterwall.frontend.menu.users = "+showMenuUsers);
        log.info("twitter.searchQuery = "+searchterm);
        log.info("twitterwall.frontend.menu.appname = "+menuAppName);
    }

    private Model setupPage(Model model) {
        Page page = new Page();
        page.setSymbol("<span class=\"glyphicon glyphicon-home\" aria-hidden=\"true\"></span>");
        page.setMenuAppName(menuAppName);
        page.setTitle("Tweets");
        page.setSubtitle(searchterm);
        page.setShowMenuUsers(showMenuUsers);
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        model.addAttribute("page", page);
        return model;
    }
}
