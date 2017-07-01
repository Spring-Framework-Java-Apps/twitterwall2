package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.frontend.model.Page;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;

import java.util.List;

/**
 * Created by tw on 10.06.17.
 */
@Controller
public class TweetsController {

    private static final Logger log = LoggerFactory.getLogger(TweetsController.class);

    private final TweetService tweetService;

    private final HashTagService hashTagService;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Autowired
    public TweetsController(TweetService tweetService, HashTagService hashTagService) {
        this.tweetService = tweetService;
        this.hashTagService = hashTagService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        return tweets(model);
    }

    @RequestMapping("/tweets")
    public String tweets(Model model) {
        logEnv();
        model = setupPage(model);
        List<Tweet> latest = tweetService.getLatestTweets();
        model.addAttribute("latestTweets", latest);
        return "timeline";
    }

    private void logEnv(){
        log.info("twitterwall.frontend.theme = "+theme);
        log.info("twitterwall.frontend.info.webpage = "+infoWebpage);
        log.info("twitterwall.frontend.menu.users = "+showMenuUsers);
        log.info("twitter.searchQuery = "+searchterm);
        log.info("twitterwall.frontend.menu.appname = "+menuAppName);
    }

    private Model setupPage(Model model) {
        Page page = new Page();
        page.setSymbol(Symbols.HOME.toString());
        page.setMenuAppName(menuAppName);
        page.setTitle("Tweets");
        page.setSubtitle(searchterm);
        page.setShowMenuUsers(showMenuUsers);
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        page.setHistoryBack(true);
        model.addAttribute("page", page);
        return model;
    }

}
