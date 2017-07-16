package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.HashTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.oodm.entities.HashTag.HASHTAG_TEXT_PATTERN;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping(path="/hashtag")
public class HashTagController extends AbstractTwitterwallController {

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page, Model model){
        logEnv();
        String subtitle = "HashTag";
        String title = "All";
        String symbol = Symbols.HASHTAG.toString();
        model = setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC,"text");
        Page<HashTag> myPageContent = hashTagService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "/hashtag/all";
    }

    @RequestMapping(path="/{hashtagText}")
    public String hashTagFromTweetsAndUsers(
        @RequestParam(name= "pageTweet" ,defaultValue=""+FIRST_PAGE_NUMBER) int pageTweet,
        @RequestParam(name= "pageUser" ,defaultValue=""+FIRST_PAGE_NUMBER) int pageUser,
        @PathVariable String hashtagText, Model model)
    {
        logEnv();
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(hashtagText);
        if (m.matches()) {
            Pageable pageRequestTweet = new PageRequest(pageTweet, pageSize);
            Pageable pageRequestUser = new PageRequest(pageUser, pageSize);
            String subtitle = "Tweets und User für HashTag";
            String title = hashtagText;
            String symbol = Symbols.HASHTAG.toString();
            model = setupPage(model,title,subtitle,symbol);
            //
            Page<Tweet> tweets = tweetService.findTweetsForHashTag(hashtagText,pageRequestTweet);
            model.addAttribute("latestTweets", tweets);
            //
            Page<User> users = userService.getUsersForHashTag(hashtagText,pageRequestUser);
            model.addAttribute("users", users);
            //
            model.addAttribute("hashtagText"+hashtagText);
            return "/hashtag/hashtagText";
            //return "timeline";
        } else {
            throw new IllegalArgumentException("/hashtag/"+hashtagText);
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
        boolean hasNext;
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, pageSize);
        do {
            Page<HashTag> myPage = hashTagService.getAll(pageRequest);
            hasNext = myPage.hasNext();
            pageRequest = pageRequest.next();
            for (HashTag hashTag : myPage) {
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
        }
        while(hasNext);
        model.addAttribute("hashTagsTweets", hashTagsTweets);
        model.addAttribute("hashTagsUsers", hashTagsUsers);
        return "/hashtag/overview";
        //return "tags";
    }

    private static final Logger log = LoggerFactory.getLogger(HashTagController.class);

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

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
