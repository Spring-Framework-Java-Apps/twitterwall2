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
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.frontend.content.Symbols;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

import javax.persistence.EntityNotFoundException;

/**
 * Created by tw on 12.06.17.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static String PATH="user";

    @RequestMapping("/all")
    public String getAll(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
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
        String subtitle = "All Users";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/all";
    }

    @RequestMapping("/{id}")
    public String getUserForId(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        @PathVariable("id") User user, Model model
    ) {
        if(user == null){
            throw new EntityNotFoundException();
        } else {
            Pageable pageRequest = new PageRequest(
                    page,
                    frontendProperties.getPageSize(),
                    Sort.Direction.DESC,
                    "createdAt"
            );
            Page<Tweet> latestTweets = tweetService.findTweetsForUser(user,pageRequest);
            String symbol = Symbols.USER_PROFILE.toString();
            String title = "@" + user.getScreenName();
            String subtitle = user.getName();
            model = contentFactory.setupPage(model, title, subtitle, symbol);
            model.addAttribute("user", user);
            model.addAttribute("latestTweets",latestTweets);
            return "user/id";
        }
    }

    @RequestMapping("/screenName/{screenName}")
    public String getUserForScreeName(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        @PathVariable String screenName, Model model
    ) {
        if (User.isValidScreenName(screenName)) {
            User user = userService.findByScreenName(screenName);
            if(user==null){
                String symbol = Symbols.USER_PROFILE.toString();
                String title = "404";
                String subtitle = "404: no user found for  @"+screenName;
                model = contentFactory.setupPage(model, title, subtitle, symbol);
                return "user/id";
            } else {
                String symbol = Symbols.USER_PROFILE.toString();
                String title = "@" + user.getScreenName();
                String subtitle = user.getName();
                model = contentFactory.setupPage(model, title, subtitle, symbol);
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
            String symbol = Symbols.USER_PROFILE.toString();
            String title = "400";
            String subtitle = "400: screenName not valid: for  /user/screenName/"+screenName;
            model = contentFactory.setupPage(model, title, subtitle, symbol);
            return "user/id";
        }
    }

    @RequestMapping("/list/tweets")
    public String getTweetingUsers(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
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
        String symbol = Symbols.USER_TWEETING.toString();
        String subtitle = "With one or more Tweets";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/allWithTweets";
    }

    @RequestMapping("/list/notyetfriends")
    public String getNotYetFriendUsers(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
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
        String subtitle = "Not Yet Friends";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/friendsNotYet";
    }

    @RequestMapping("/list/friends")
    public String getFriendUsers(
            @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
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
        String subtitle = "Friends";
        model = contentFactory.setupPage(model, title, subtitle,  symbol);
        return "user/list/friends";
    }

    @RequestMapping("/list/follower")
    public String getFollower(
            @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
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
        String subtitle = "Follower";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/follower";
    }

    @RequestMapping("/list/notyetfollower")
    public String getNotYetFollower(
            @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
            Model model
    ) {
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "screenName"
        );
        Page<User> users = userService.getNotYetFollower(pageRequest);
        model.addAttribute("users", users);
        String symbol = Symbols.USER_FOLLOWER.toString();
        String subtitle = "Follower";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/followerNotYet";
    }

    @RequestMapping("/list/onlist")
    public String getOnList(
            @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
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
        String subtitle = "On List";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/onlist";
    }

    @RequestMapping("/list/notyetonlist")
    public String getNotYetOnList(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
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
        String subtitle = "Not Yet On List";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/onlistNotYet";
    }

    @RequestMapping("/list/usersWhoAreFollowersButNotFriends")
    public String findUsersWhoAreFollowersButNotFriends(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "screenName"
        );
        model.addAttribute("users", userService.findUsersWhoAreFollowersButNotFriends(pageRequest));
        String symbol = Symbols.USER_CONNECTIONS.toString();
        String subtitle = "Users who are Followers but not Friends";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/usersWhoAreFollowersButNotFriends";
    }

    @RequestMapping("/list/usersWhoAreFollowersAndFriends")
    public String findUsersWhoAreFollowersAndFriends(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "screenName"
        );
        model.addAttribute("users", userService.findUsersWhoAreFollowersAndFriends(pageRequest));
        String symbol = Symbols.USER_CONNECTIONS.toString();
        String subtitle = "Users who are Followers AND Friends";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/usersWhoAreFollowersAndFriends";
    }

    @RequestMapping("/list/usersWhoAreFriendsButNotFollowers")
    public String findUsersWhoAreFriendsButNotFollowers(
        @RequestParam(name= "page", defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "screenName"
        );
        model.addAttribute("users", userService.findUsersWhoAreFriendsButNotFollowers(pageRequest));
        String symbol = Symbols.USER_CONNECTIONS.toString();
        String subtitle = "Users who are Friends but not Followers";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        return "user/list/usersWhoAreFriendsButNotFollowers";
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final TweetService tweetService;

    private final FrontendProperties frontendProperties;

    private final ContentFactory contentFactory;

    private static String title = "Users";

    @Autowired
    public UserController(
            UserService userService,
            TweetService tweetService,
            FrontendProperties frontendProperties,
            ContentFactory contentFactory
    ) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.frontendProperties = frontendProperties;
        this.contentFactory = contentFactory;
    }
}
