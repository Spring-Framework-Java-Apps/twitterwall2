package org.woehlke.twitterwall.frontend.controller.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.frontend.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 12.07.17.
 */
@Controller
public class PagesController extends AbstractTwitterwallController {

    @RequestMapping("/")
    public ModelAndView index(Model model) {
        return new ModelAndView("redirect:/tweet/all");
    }

    @RequestMapping("/imprint")
    public String imprint(Model model) {
        log.info("-----------------------------------------");
        logEnv();
        String symbol = Symbols.IMPRINT.toString();
        String title = "Impressum";
        String subtitle = imprintSubtitle;
        model = super.setupPage(model, title, subtitle, symbol);
        String screenName = imprintScreenName;
        super.addUserForScreenName(model,screenName);
        log.info("-----------------------------------------");
        return "imprint";
    }

    @RequestMapping("/hashtags")
    public String hashTags(Model model) {
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

    private static final Logger log = LoggerFactory.getLogger(PagesController.class);

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

    private final TwitterApiService twitterApiService;

    private final StoreOneTweet storeOneTweet;

    private final StoreUserProfile storeUserProfile;

    private final TaskService taskService;

    private final HashTagService hashTagService;

    private final TweetService tweetService;

    private final UserService userService;

    @Autowired
    public PagesController(TwitterApiService twitterApiService, StoreOneTweet storeOneTweet, StoreUserProfile storeUserProfile, TaskService taskService, HashTagService hashTagService, TweetService tweetService, UserService userService) {
        this.twitterApiService = twitterApiService;
        this.storeOneTweet = storeOneTweet;
        this.storeUserProfile = storeUserProfile;
        this.taskService = taskService;
        this.hashTagService = hashTagService;
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSetWithTesting(taskService,twitterApiService,storeOneTweet,storeUserProfile,userService,menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
