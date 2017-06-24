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
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

/**
 * Created by tw on 12.06.17.
 */
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.theme}")
    private String theme;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/all")
    public String all(Model model) {
        model.addAttribute("users", userService.getAll());
        String symbol = "<i class=\"fa fa-user\" aria-hidden=\"true\"></i>";
        String subtitle = "All";
        model = setupPage(model, subtitle, symbol);
        return "user";
    }

    @RequestMapping("/user/tweets")
    public String getTweetingUsers(Model model) {
        List<User> tweetingUsers = userService.getTweetingUsers();
        model.addAttribute("users", tweetingUsers);
        String symbol = "<i class=\"fa fa-users\" aria-hidden=\"true\"></i>";
        String subtitle = "With one or more Tweets";
        model = setupPage(model, subtitle, symbol);
        return "user";
    }

    @RequestMapping("/user/notyetfriends")
    public String getNotYetFriendUsers(Model model) {
        model.addAttribute("users", userService.getNotYetFriendUsers());
        String symbol = "<i class=\"fa fa-plus-square\" aria-hidden=\"true\"></i>";
        String subtitle = "Not Yet Friends";
        model = setupPage(model, subtitle, symbol);
        return "user";
    }

    private Model setupPage(Model model, String subtitle, String symbol) {
        Page page = new Page();
        page.setSymbol(symbol);
        page.setMenuAppName(menuAppName);
        page.setTitle(subtitle);
        page.setSubtitle("Users");
        page.setShowMenuUsers(showMenuUsers);
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        model.addAttribute("page", page);
        return model;
    }
}
