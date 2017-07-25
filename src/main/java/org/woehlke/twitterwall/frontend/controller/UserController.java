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
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

/**
 * Created by tw on 12.06.17.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static String PATH="user";

    @RequestMapping("/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize(), Sort.Direction.ASC,"screenName");
        model.addAttribute("users", userService.getAll(pageRequest));
        String symbol = Symbols.USER_ALL.toString();
        String title = "All Users";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/all";
    }

    @RequestMapping("/{screenName}")
    public String getUserForScreeName(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        @PathVariable String screenName, Model model) {
        if (User.isValidScreenName(screenName)) {
            User user = userService.findByScreenName(screenName);
            if(user==null){
                throw new IllegalArgumentException("/user/"+ screenName);
            }
            Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize(), Sort.Direction.DESC,"createdAt");
            Page<Tweet> tweetsForUser = tweetService.findTweetsForUser(user,pageRequest);
            String symbol = Symbols.PROFILE.toString();
            String title = "@" + user.getScreenName();
            String subtitle = user.getName();
            model = controllerHelper.setupPage(model, title, subtitle, symbol);
            model.addAttribute("user", user);
            model.addAttribute("latestTweets",tweetsForUser);
            return "user/screenName";
        } else {
            throw new IllegalArgumentException("/user/"+ screenName);
        }
    }

    @RequestMapping("/tweets")
    public String getTweetingUsers(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize(), Sort.Direction.ASC,"screenName");
        Page<User> tweetingUsers = userService.getTweetingUsers(pageRequest);
        model.addAttribute("users", tweetingUsers);
        String symbol = Symbols.USER_TWEETS.toString();
        String title = "With one or more Tweets";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/tweets";
    }

    @RequestMapping("/notyetfriends")
    public String getNotYetFriendUsers(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize(), Sort.Direction.ASC,"screenName");
        model.addAttribute("users", userService.getNotYetFriendUsers(pageRequest));
        String symbol = Symbols.USER_NOT_YET_FRIENDS.toString();
        String title = "Not Yet Friends";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/notyetfriends";
    }

    @RequestMapping("/notyetonlist")
    public String getNotYetOnList(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize(), Sort.Direction.ASC,"screenName");
        model.addAttribute("users", userService.getNotYetOnList(pageRequest));
        String symbol = Symbols.USER_NOT_YET_ON_LIST.toString();
        String title = "Not Yet On List";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/notyetonlist";
    }

    @RequestMapping("/onlist")
    public String getOnList(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,Model model) {
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize(), Sort.Direction.ASC,"screenName");
        Page<User> usersOnList = userService.getOnList(pageRequest);
        model.addAttribute("users", usersOnList);
        String symbol = Symbols.LEAF.toString();
        String title = "On List";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/onlist";
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final TweetService tweetService;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final ControllerHelper controllerHelper;

    private static String subtitle = "Users";

    @Autowired
    public UserController(UserService userService, TweetService tweetService, TwitterwallFrontendProperties twitterwallFrontendProperties, ControllerHelper controllerHelper) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.controllerHelper = controllerHelper;
    }
}
