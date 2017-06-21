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
import org.woehlke.twitterwall.oodm.exceptions.ControllerRequestParameterSyntaxException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;

import javax.xml.ws.http.HTTPException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.process.ScheduledTasksFacade.ID_TWITTER_TO_FETCH_FOR_TWEET_TEST;

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

    @Autowired
    public TimelineController(TweetService tweetService, HashTagService hashTagService) {
        this.tweetService = tweetService;
        this.hashTagService = hashTagService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model = setupPage(model);
        if(fetchTestData) {
            List<Tweet> list = tweetService.getTestTweetsForTweetTest();
            List<Tweet> latest = tweetService.getLatestTweets();
            list.addAll(latest);
            model.addAttribute("latestTweets", list);
        } else {
            List<Tweet> latest = tweetService.getLatestTweets();
            model.addAttribute("latestTweets", latest);
        }
        return "timeline";
    }

    @RequestMapping("/tweets")
    public String tweets(Model model) {
        model = setupPage(model);
        if(fetchTestData) {
            List<Tweet> list = tweetService.getTestTweetsForTweetTest();
            List<Tweet> latest = tweetService.getLatestTweets();
            list.addAll(latest);
            model.addAttribute("latestTweets", list);
        } else {
            List<Tweet> latest = tweetService.getLatestTweets();
            model.addAttribute("latestTweets", latest);
        }
        return "timeline";
    }

    @RequestMapping("/hashtags")
    public String hashTags(Model model) {
        model = setupPage(model);
        List<HashTagCounted> hashTags = new ArrayList<>();
        for(HashTag hashTag : hashTagService.getAll()){
            long number=tweetService.countTweetsForHashTag(hashTag.getText());
            String text=hashTag.getText();
            HashTagCounted c = new HashTagCounted(number,text);
            hashTags.add(c);
        }
        model.addAttribute("hashTags",hashTags);
        return "tags";
    }

    @RequestMapping("/hashtag/{hashtagText}")
    public String hashTag(@PathVariable String hashtagText, Model model) {
        Pattern p = Pattern.compile("^[öÖäÄüÜßa-zA-Z0-9_]{1,139}$");
        Matcher m = p.matcher(hashtagText);
        if(m.matches()) {
            Page page = new Page();
            page.setMenuAppName(menuAppName);
            page.setTitle("Tweets für HashTag");
            page.setSubtitle("#"+hashtagText);
            page.setHistoryBack(true);
            page.setShowMenuUsers(showMenuUsers);
            model.addAttribute("page",page);
            List<Tweet> tweets = tweetService.getTweetsForHashTag(hashtagText);
            model.addAttribute("latestTweets", tweets);
            return "timeline";
        } else {
            throw new ControllerRequestParameterSyntaxException("/hashtag/{hashtagText}",hashtagText);
        }
    }

    private Model setupPage(Model model){
        Page page = new Page();
        page.setMenuAppName(menuAppName);
        page.setTitle("Tweets");
        page.setSubtitle(searchterm);
        page.setShowMenuUsers(showMenuUsers);
        model.addAttribute("page",page);
        return model;
    }

}
