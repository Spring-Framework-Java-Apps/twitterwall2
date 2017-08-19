package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.frontend.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;

import javax.persistence.EntityNotFoundException;


/**
 * Created by tw on 10.06.17.
 */
@Controller
@RequestMapping("/tweet")
public class TweetController {

    @RequestMapping("/all")
    public String getLatestTweets(
            @RequestParam(name= "page", defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
            Model model
    ) {
        String title = "Tweets";
        model = controllerHelper.setupPage(
            model,
            title,
            twitterProperties.getSearchQuery(),
            Symbols.STARTPAGE.toString()
        );
        String sortByColumn = "createdAt";
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            sortByColumn
        );
        Page<Tweet> latest = tweetService.getAll(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    @RequestMapping("/{id}")
    public String getTweetById(
        @PathVariable("id") Tweet tweet, Model model
    ) {
        if(tweet == null){
            throw new EntityNotFoundException();
        } else {
            String title = "Tweet";
            model = controllerHelper.setupPage(
                    model,
                    title,
                    twitterProperties.getSearchQuery(),
                    Symbols.HOME.toString()
            );
            model.addAttribute("tweet", tweet);
            return "tweet/id";
        }
    }

    @RequestMapping("/timeline/home")
    public String getHomeTimeline(
        @RequestParam(name= "page", defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        String title = "Tweets";
        model = controllerHelper.setupPage(
            model,
            title,
            "Home Timneline",
            Symbols.TWEETS_HOME_TIMELINE.toString()
        );
        String sortByColumn = "createdAt";
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            sortByColumn
        );
        Page<Tweet> latest = tweetService.getHomeTimeline(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    @RequestMapping("/timeline/user")
    public String getUserTimeline(
        @RequestParam(name= "page", defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        String title = "Tweets";
        model = controllerHelper.setupPage(
            model,
            title,
            "User Timeline",
            Symbols.TWEETS_USER_TIMELINE.toString()
        );
        String sortByColumn = "createdAt";
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            sortByColumn
        );
        Page<Tweet> latest = tweetService.getUserTimeline(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    @RequestMapping("/mentions")
    public String getMentions(
        @RequestParam(name= "page", defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        String title = "Tweets";
        model = controllerHelper.setupPage(
            model,
            title,
            "Mentions",
            Symbols.TWEETS_MENTIONS.toString()
        );
        String sortByColumn = "createdAt";
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            sortByColumn
        );
        Page<Tweet> latest = tweetService.getMentions(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    @RequestMapping("/favorites")
    public String getFavorites(
        @RequestParam(name= "page", defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        String title = "Tweets";
        model = controllerHelper.setupPage(
            model,
            title,
            "Favorites",
            Symbols.TWEETS_FAVORITES.toString()
        );
        String sortByColumn = "createdAt";
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            sortByColumn
        );
        Page<Tweet> latest = tweetService.getFavorites(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    @RequestMapping("/retweets")
    public String getRetweetsOfMe(
        @RequestParam(name= "page", defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        String title = "Tweets";
        model = controllerHelper.setupPage(
            model,
            title,
            "Retweets Of Me",
            Symbols.TWEETS_RETWEETS_OF_ME.toString()
        );
        String sortByColumn = "createdAt";
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            sortByColumn
        );
        Page<Tweet> latest = tweetService.getRetweetsOfMe(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    private static final Logger log = LoggerFactory.getLogger(TweetController.class);

    private final TweetService tweetService;

    private final ControllerHelper controllerHelper;

    private final FrontendProperties frontendProperties;

    private final TwitterProperties twitterProperties;

    @Autowired
    public TweetController(
            TweetService tweetService,
            ControllerHelper controllerHelper,
            FrontendProperties frontendProperties,
            TwitterProperties twitterProperties
    ) {
        this.tweetService = tweetService;
        this.controllerHelper = controllerHelper;
        this.frontendProperties = frontendProperties;
        this.twitterProperties = twitterProperties;
    }

}
