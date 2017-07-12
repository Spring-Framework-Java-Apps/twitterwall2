package org.woehlke.twitterwall.frontend.controller;

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
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Controller("/user")
public class UserController extends AbstractTwitterwallController {

    @RequestMapping("/all")
    public String all(Model model) {
        model.addAttribute("users", userService.getAll());
        String symbol = Symbols.USER_ALL.toString();
        String title = "All Users";
        model = super.setupPage(model, title, subtitle, symbol);
        return "user";
    }

    @RequestMapping("/{screenName}")
    public String follower(@PathVariable String screenName, Model model) {
        if (User.isValidScreenName(screenName)) {
            User user = userService.findByScreenName(screenName);
            List<Tweet> tweetsForUser = tweetService.getTweetsForUser(user);
            String symbol = Symbols.PROFILE.toString();
            String title = "@" + user.getScreenName();
            String subtitle = user.getName();
            model = super.setupPage(model, title, subtitle, symbol);
            model.addAttribute("user", user);
            model.addAttribute("latestTweets",tweetsForUser);
            return "profile";
        } else {
            throw new IllegalArgumentException("/profile/"+ screenName);
        }
    }

    @RequestMapping("/tweets")
    public String getTweetingUsers(Model model) {
        List<User> tweetingUsers = userService.getTweetingUsers();
        model.addAttribute("users", tweetingUsers);
        String symbol = Symbols.USER_TWEETS.toString();
        String title = "With one or more Tweets";
        model = super.setupPage(model, title, subtitle, symbol);
        return "user";
    }

    @RequestMapping("/notyetfriends")
    public String getNotYetFriendUsers(Model model) {
        model.addAttribute("users", userService.getNotYetFriendUsers());
        String symbol = Symbols.USER_NOT_YET_FRIENDS.toString();
        String title = "Not Yet Friends";
        model = setupPage(model, title, subtitle, symbol);
        return "user";
    }

    @RequestMapping("/notyetonlist")
    public String getNotYetOnList(Model model) {
        model.addAttribute("users", userService.getNotYetOnList());
        String symbol = Symbols.USER_NOT_YET_ON_LIST.toString();
        String title = "Not Yet On List";
        model = setupPage(model, title, subtitle, symbol);
        return "user";
    }

    @RequestMapping("/onlist")
    public String getOnList(Model model) {
        List<User> usersOnList = userService.getOnList();
        model.addAttribute("users", usersOnList);
        String symbol = Symbols.LEAF.toString();
        String title = "On List";
        model = setupPage(model, title, subtitle, symbol);
        return "user";
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

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
    public UserController(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSet(menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }

    private static String subtitle = "Users";
}
