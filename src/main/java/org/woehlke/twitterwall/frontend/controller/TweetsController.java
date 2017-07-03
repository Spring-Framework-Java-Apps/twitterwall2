package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Controller
public class TweetsController extends AbstractTwitterwallController {

    private static final Logger log = LoggerFactory.getLogger(TweetsController.class);

    private final TweetService tweetService;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Value("${twitterwall.context.test}")
    private boolean contextTest;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;

    @Autowired
    public TweetsController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        return tweets(model);
    }

    @RequestMapping("/tweets")
    public String tweets(Model model) {
        logEnv();
        model = super.setupPage(model,"Tweets",searchterm,Symbols.HOME.toString());
        List<Tweet> latest = tweetService.getLatestTweets();
        model.addAttribute("latestTweets", latest);
        return "timeline";
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSet(menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
