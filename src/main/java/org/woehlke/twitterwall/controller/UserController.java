package org.woehlke.twitterwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.service.UserService;

/**
 * Created by tw on 12.06.17.
 */
@Controller
public class UserController {

    private final UserService userService;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/follower")
    public String follower(Model model) {
        model.addAttribute("users", userService.getFollower());
        model = setupPage(model,"Follower");
        return "user/user";
    }

    @RequestMapping("/user/friends")
    public String friends(Model model) {
        model.addAttribute("users", userService.getFriends());
        model = setupPage(model,"Friends");
        return "user/user";
    }

    @RequestMapping("/user/all")
    public String all(Model model) {
        model.addAttribute("users", userService.getAll());
        model = setupPage(model,"All");
        return "user/user";
    }

    @RequestMapping("/user/tweets")
    public String getTweetingUsers(Model model) {
        model.addAttribute("users", userService.getTweetingUsers());
        model = setupPage(model,"Tweets");
        return "user/user";
    }

    private Model setupPage(Model model,String subtitle){
        Page page = new Page();
        page.setMenuAppName(menuAppName);
        page.setTitle("Users");
        page.setSubtitle(subtitle);
        model.addAttribute("page",page);
        return model;
    }
}
