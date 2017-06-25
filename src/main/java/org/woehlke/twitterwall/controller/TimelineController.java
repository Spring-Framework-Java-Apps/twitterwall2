package org.woehlke.twitterwall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.HashTagCounted;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.exceptions.controller.ControllerRequestParameterSyntaxException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.entities.HashTag.HASHTAG_TEXT_PATTERN;

/**
 * Created by tw on 10.06.17.
 */
@Controller
public class TimelineController {

    private static final Logger log = LoggerFactory.getLogger(TimelineController.class);

    private final TweetService tweetService;

    private final HashTagService hashTagService;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitterwall.twitter.fetchTestData}")
    private boolean fetchTestData;

    @Value("${twitterwall.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.theme}")
    private String theme;

    @Autowired
    public TimelineController(TweetService tweetService, HashTagService hashTagService) {
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
        if (fetchTestData) {
            try {
                List<Tweet> list = tweetService.getTestTweetsForTweetTest();
                latest.addAll(list);
            } catch (Exception e){
                log.error("getTestTweetsForTweetTest: "+e.getMessage());
            }
        }
        model.addAttribute("latestTweets", latest);
        return "timeline";
    }

    @RequestMapping("/hashtags")
    public String hashTags(Model model) {
        logEnv();
        Page page = new Page();
        page.setSymbol("<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>");
        page.setMenuAppName(menuAppName);
        page.setTitle("HashTags");
        page.setSubtitle(searchterm);
        page.setShowMenuUsers(showMenuUsers);
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        model.addAttribute("page", page);
        List<HashTagCounted> hashTags = new ArrayList<>();
        for (HashTag hashTag : hashTagService.getAll()) {
            long number = tweetService.countTweetsForHashTag(hashTag.getText());
            String text = hashTag.getText();
            HashTagCounted c = new HashTagCounted(number, text);
            hashTags.add(c);
        }
        model.addAttribute("hashTags", hashTags);
        return "tags";
    }

    @RequestMapping("/hashtag/{hashtagText}")
    public String hashTag(@PathVariable String hashtagText, Model model) {
        logEnv();
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        if (m.matches()) {
            Page page = new Page();
            page.setSymbol("<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>");
            page.setMenuAppName(menuAppName);
            page.setTitle("Tweets für HashTag");
            page.setSubtitle("#" + hashtagText);
            page.setHistoryBack(true);
            page.setShowMenuUsers(showMenuUsers);
            page.setTwitterSearchTerm(searchterm);
            page.setInfoWebpage(infoWebpage);
            page.setTheme(theme);
            model.addAttribute("page", page);
            List<Tweet> tweets = tweetService.getTweetsForHashTag(hashtagText);
            model.addAttribute("latestTweets", tweets);
            return "timeline";
        } else {
            throw new ControllerRequestParameterSyntaxException("/hashtag/{hashtagText}", hashtagText);
        }
    }

    private void logEnv(){
        log.info("twitterwall.theme = "+theme);
        log.info("twitterwall.info.webpage = "+infoWebpage);
        log.info("twitterwall.twitter.fetchTestData = "+fetchTestData);
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
