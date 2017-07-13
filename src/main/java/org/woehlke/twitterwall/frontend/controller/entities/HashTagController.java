package org.woehlke.twitterwall.frontend.controller.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.frontend.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.entities.HashTag.HASHTAG_TEXT_PATTERN;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping(path="/hashtag")
public class HashTagController extends AbstractTwitterwallController {

    @RequestMapping(path="/tweet/{hashtagText}")
    public String hashTagFromTweets(@PathVariable String hashtagText, Model model) {
        logEnv();
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        if (m.matches()) {
            String title = "Tweets für HashTag";
            String subtitle = "#" + hashtagText;
            String symbol = Symbols.HASHTAG.toString();
            model = setupPage(model,title,subtitle,symbol);
            List<Tweet> tweets = tweetService.getTweetsForHashTag(hashtagText);
            model.addAttribute("latestTweets", tweets);
            return "timeline";
        } else {
            throw new IllegalArgumentException("/hashtag/"+hashtagText);
        }
    }

    @RequestMapping(path="/user/{hashtagText}")
    public String hashTagFromUsers(@PathVariable String hashtagText, Model model) {
        logEnv();
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        if (m.matches()) {
            String title = "Users für HashTag";
            String subtitle = "#" + hashtagText;
            String symbol = Symbols.HASHTAG.toString();
            model = setupPage(model,title,subtitle,symbol);
            List<User> users = userService.getUsersForHashTag(hashtagText);
            model.addAttribute("users", users);
            return "user";
        } else {
            throw new IllegalArgumentException("/user/hashtag/"+hashtagText);
        }
    }

    @RequestMapping(path="/overview")
    public String hashTagsOverview(Model model) {
        String msg = "/hashtags: ";
        logEnv();
        String title = "HashTags";
        String subtitle = searchterm;
        String symbol = Symbols.HASHTAG.toString();
        model = setupPage(model,title,subtitle,symbol);
        List<HashTagCounted> hashTagsTweets = new ArrayList<>();
        List<HashTagCounted> hashTagsUsers = new ArrayList<>();
        for (HashTag hashTag : hashTagService.getAll()) {
            String text = hashTag.getText();
            try {
                long numberTweets = tweetService.countTweetsForHashTag(hashTag.getText());
                if(numberTweets > 0) {
                    HashTagCounted c = new HashTagCounted(numberTweets, text);
                    hashTagsTweets.add(c);
                }
            } catch (IllegalArgumentException e){
                log.warn(msg+"tweetService.countTweetsForHashTag: "+e.getMessage());
            }
            try {
                long numberUsers = userService.countUsersForHashTag(hashTag.getText());
                if(numberUsers > 0){
                    HashTagCounted c = new HashTagCounted(numberUsers,text);
                    hashTagsUsers.add(c);
                }
            } catch (IllegalArgumentException e){
                log.warn(msg+"tweetService.countTweetsForHashTag: "+e.getMessage());
            }
        }
        model.addAttribute("hashTagsTweets", hashTagsTweets);
        model.addAttribute("hashTagsUsers", hashTagsUsers);
        return "tags";
    }

    private static final Logger log = LoggerFactory.getLogger(HashTagController.class);

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

    @Value("${twitterwall.frontend.imprint.subtitle}")
    private String imprintSubtitle;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;

    private final HashTagService hashTagService;

    private final TweetService tweetService;

    private final UserService userService;

    @Autowired
    public HashTagController(HashTagService hashTagService, TweetService tweetService, UserService userService) {
        this.hashTagService = hashTagService;
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSet(menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
