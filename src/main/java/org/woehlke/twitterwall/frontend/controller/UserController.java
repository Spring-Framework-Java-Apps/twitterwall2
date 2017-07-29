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
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
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
    public String getAll(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            "screenName"
        );
        model.addAttribute("users", userService.getAll(pageRequest));
        String symbol = Symbols.USER_ALL.toString();
        String title = "All Users";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/all";
    }

    @RequestMapping("/{id}")
    public String getUserForId(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        @PathVariable("id") User user, Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.DESC,
            "createdAt"
        );
        Page<Tweet> latestTweets = tweetService.findTweetsForUser(user,pageRequest);
        String symbol = Symbols.PROFILE.toString();
        String title = "@" + user.getScreenName();
        String subtitle = user.getName();
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        model.addAttribute("user", user);
        model.addAttribute("latestTweets",latestTweets);
        return "user/id";
    }

    @RequestMapping("/screenName/{screenName}")
    public String getUserForScreeName(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        @PathVariable String screenName, Model model
    ) {

        if (User.isValidScreenName(screenName)) {
            User user = userService.findByScreenName(screenName);
            if(user==null){
                String symbol = Symbols.PROFILE.toString();
                String title = "404";
                String subtitle = "404: no user found for  @"+screenName;
                model = controllerHelper.setupPage(model, title, subtitle, symbol);
                return "user/id";
            } else {
                String symbol = Symbols.PROFILE.toString();
                String title = "@" + user.getScreenName();
                String subtitle = user.getName();
                model = controllerHelper.setupPage(model, title, subtitle, symbol);
                Pageable pageRequest = new PageRequest(
                        page,
                        frontendProperties.getPageSize(),
                        Sort.Direction.DESC,
                        "createdAt"
                );
                Page<Tweet> latestTweets = tweetService.findTweetsForUser(user,pageRequest);

                model.addAttribute("user", user);
                model.addAttribute("latestTweets",latestTweets);
                return "user/id";
            }
        } else {
            String symbol = Symbols.PROFILE.toString();
            String title = "400";
            String subtitle = "400: screenName not valid: for  /user/screenName/"+screenName;
            model = controllerHelper.setupPage(model, title, subtitle, symbol);
            return "user/id";
        }
    }

    @RequestMapping("/list/tweets")
    public String getTweetingUsers(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            "screenName"
        );
        Page<User> tweetingUsers = userService.getTweetingUsers(pageRequest);
        model.addAttribute("users", tweetingUsers);
        String symbol = Symbols.USER_TWEETS.toString();
        String title = "With one or more Tweets";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/list/allWithTweets";
    }

    @RequestMapping("/list/notyetfriends")
    public String getNotYetFriendUsers(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            "screenName"
        );
        Page<User> users = userService.getNotYetFriendUsers(pageRequest);
        model.addAttribute("users", users);
        String symbol = Symbols.USER_NOT_YET_FRIENDS.toString();
        String title = "Not Yet Friends";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/list/notyetfriends";
    }

    @RequestMapping("/list/friends")
    public String getFriendUsers(
            @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
            Model model
    ) {
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "screenName"
        );
        Page<User> users = userService.getFriends(pageRequest);
        model.addAttribute("users", users);
        String symbol = Symbols.USER_FRIENDS.toString();
        String title = "Friends";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/list/friends";
    }

    @RequestMapping("/list/follower")
    public String getFollower(
            @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
            Model model
    ) {
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "screenName"
        );
        Page<User> users = userService.getFollower(pageRequest);
        model.addAttribute("users", users);
        String symbol = Symbols.USER_FOLLOWER.toString();
        String title = "Follower";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/list/follower";
    }

    @RequestMapping("/list/notyetonlist")
    public String getNotYetOnList(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            "screenName"
        );
        model.addAttribute("users", userService.getNotYetOnList(pageRequest));
        String symbol = Symbols.USER_NOT_YET_ON_LIST.toString();
        String title = "Not Yet On List";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/list/notyetonlist";
    }

    @RequestMapping("/list/onlist")
    public String getOnList(
        @RequestParam(name= "page", defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page,
        Model model
    ) {
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            "screenName"
        );
        Page<User> usersOnList = userService.getOnList(pageRequest);
        model.addAttribute("users", usersOnList);
        String symbol = Symbols.LEAF.toString();
        String title = "On List";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        return "user/list/onlist";
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final TweetService tweetService;

    private final FrontendProperties frontendProperties;

    private final ControllerHelper controllerHelper;

    private static String subtitle = "Users";

    @Autowired
    public UserController(
            UserService userService,
            TweetService tweetService,
            FrontendProperties frontendProperties,
            ControllerHelper controllerHelper
    ) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.frontendProperties = frontendProperties;
        this.controllerHelper = controllerHelper;
    }
}
