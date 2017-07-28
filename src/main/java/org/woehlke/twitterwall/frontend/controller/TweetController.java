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
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;


/**
 * Created by tw on 10.06.17.
 */
@Controller
@RequestMapping("/tweet")
public class TweetController {

    @RequestMapping("/all")
    public String getLatestTweets(
            @RequestParam(
                    name= "page" ,
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        model = controllerHelper.setupPage(
                model,
                "Tweets",
                twitterProperties.getSearchQuery(),Symbols.HOME.toString()
        );
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize(), Sort.Direction.DESC,"createdAt");
        Page<Tweet> latest = tweetService.getAll(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    @RequestMapping("/id/{id}")
    public String getLatestTweets(
            @PathVariable("id") Tweet tweet,  Model model
    ) {
        model = controllerHelper.setupPage(
                model,
                "Tweet",
                twitterProperties.getSearchQuery(),
                Symbols.HOME.toString()
        );
        model.addAttribute("tweet", tweet);
        return "tweet/one";
    }

    private static final Logger log = LoggerFactory.getLogger(TweetController.class);

    private final TweetService tweetService;

    private final ControllerHelper controllerHelper;

    private final FrontendProperties frontendProperties;

    private final TwitterProperties twitterProperties;

    @Autowired
    public TweetController(TweetService tweetService, ControllerHelper controllerHelper, FrontendProperties frontendProperties, TwitterProperties twitterProperties) {
        this.tweetService = tweetService;
        this.controllerHelper = controllerHelper;
        this.frontendProperties = frontendProperties;
        this.twitterProperties = twitterProperties;
    }
}
