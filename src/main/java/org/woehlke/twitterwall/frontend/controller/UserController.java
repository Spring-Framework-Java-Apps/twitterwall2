package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Controller
@RequestMapping("/user")
public class UserController extends AbstractTwitterwallController {

    @RequestMapping("/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page, Model model) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        model.addAttribute("users", userService.getAll(pageRequest));
        String symbol = Symbols.USER_ALL.toString();
        String title = "All Users";
        model = super.setupPage(model, title, subtitle, symbol);
        return "user/all";
    }

    @RequestMapping("/{screenName}")
    public String getUserForScreeName(
        @RequestParam(name= "page", defaultValue=""+FIRST_PAGE_NUMBER) int page,
        @PathVariable String screenName, Model model) {
        if (User.isValidScreenName(screenName)) {
            Pageable pageRequest = new PageRequest(page, pageSize);
            User user = userService.findByScreenName(screenName);
            Page<Tweet> tweetsForUser = tweetService.getTweetsForUser(user,pageRequest);
            String symbol = Symbols.PROFILE.toString();
            String title = "@" + user.getScreenName();
            String subtitle = user.getName();
            model = super.setupPage(model, title, subtitle, symbol);
            model.addAttribute("user", user);
            model.addAttribute("latestTweets",tweetsForUser);
            return "user/screenName";
        } else {
            throw new IllegalArgumentException("/profile/"+ screenName);
        }
    }

    @RequestMapping("/tweets")
    public String getTweetingUsers(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        Page<User> tweetingUsers = userService.getTweetingUsers(pageRequest);
        model.addAttribute("users", tweetingUsers);
        String symbol = Symbols.USER_TWEETS.toString();
        String title = "With one or more Tweets";
        model = super.setupPage(model, title, subtitle, symbol);
        return "user/tweets";
    }

    @RequestMapping("/notyetfriends")
    public String getNotYetFriendUsers(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        model.addAttribute("users", userService.getNotYetFriendUsers(pageRequest));
        String symbol = Symbols.USER_NOT_YET_FRIENDS.toString();
        String title = "Not Yet Friends";
        model = setupPage(model, title, subtitle, symbol);
        return "user/notyetfriends";
    }

    @RequestMapping("/notyetonlist")
    public String getNotYetOnList(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        model.addAttribute("users", userService.getNotYetOnList(pageRequest));
        String symbol = Symbols.USER_NOT_YET_ON_LIST.toString();
        String title = "Not Yet On List";
        model = setupPage(model, title, subtitle, symbol);
        return "user/notyetonlist";
    }

    @RequestMapping("/onlist")
    public String getOnList(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        Page<User> usersOnList = userService.getOnList(pageRequest);
        model.addAttribute("users", usersOnList);
        String symbol = Symbols.LEAF.toString();
        String title = "On List";
        model = setupPage(model, title, subtitle, symbol);
        return "user/onlist";
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final TweetService tweetService;

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
