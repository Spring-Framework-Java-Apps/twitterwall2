package org.woehlke.twitterwall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.exceptions.FindUrlByUrlException;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UrlService urlService;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Autowired
    public UserController(UserService userService, UrlService urlService) {
        this.userService = userService;
        this.urlService = urlService;
    }

    @RequestMapping("/user/follower")
    public String follower(Model model) {
        model.addAttribute("users", userService.getFollower());
        model = setupPage(model,"Follower");
        return "user";
    }

    @RequestMapping("/user/friends")
    public String friends(Model model) {
        model.addAttribute("users", userService.getFriends());
        model = setupPage(model,"Friends");
        return "user";
    }

    @RequestMapping("/user/all")
    public String all(Model model) {
        model.addAttribute("users", userService.getAll());
        model = setupPage(model,"All");
        return "user";
    }

    @RequestMapping("/user/tweets")
    public String getTweetingUsers(Model model) {
        List<User> myTweetingUsers = new ArrayList<>();
        List<User> tweetingUsers = userService.getTweetingUsers();
        for(User user : tweetingUsers){
           String url = user.getUrl();
           try {
               if (url != null) {
                   log.info("url: "+url);
                   Url myUrl = urlService.findByUrl(url);
                   user.setUrlDisplay(myUrl.getDisplay());
                   user.setUrlExpanded(myUrl.getExpanded());
               }
           } catch (FindUrlByUrlException e){
               log.error(e.getMessage());
           }
           myTweetingUsers.add(user);
        }
        model.addAttribute("users", myTweetingUsers);
        model = setupPage(model,"Tweets");
        return "user";
    }

    @RequestMapping("/user/notyetfriends")
    public String getNotYetFriendUsers(Model model) {
        model.addAttribute("users", userService.getNotYetFriendUsers());
        model = setupPage(model,"Not Yet Friends");
        return "user";
    }

    private Model setupPage(Model model,String subtitle){
        Page page = new Page();
        page.setMenuAppName(menuAppName);
        page.setTitle("Users");
        page.setSubtitle(subtitle);
        page.setShowMenuUsers(showMenuUsers);
        model.addAttribute("page",page);
        return model;
    }
}
