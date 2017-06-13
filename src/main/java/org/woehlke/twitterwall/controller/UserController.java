package org.woehlke.twitterwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.service.MyTwitterProfileService;

/**
 * Created by tw on 12.06.17.
 */
@Controller
public class UserController {

    private final MyTwitterProfileService myTwitterProfileService;

    @Autowired
    public UserController(MyTwitterProfileService myTwitterProfileService) {
        this.myTwitterProfileService = myTwitterProfileService;
    }

    @RequestMapping("/user/follower")
    public String follower(Model model) {
        model.addAttribute("users",myTwitterProfileService.getFollower());
        model = setupPage(model,"Follower");
        return "user/user";
    }

    @RequestMapping("/user/friends")
    public String friends(Model model) {
        model.addAttribute("users",myTwitterProfileService.getFriends());
        model = setupPage(model,"Friends");
        return "user/user";
    }

    @RequestMapping("/user/all")
    public String all(Model model) {
        model.addAttribute("users",myTwitterProfileService.getAll());
        model = setupPage(model,"All");
        return "user/user";
    }

    private Model setupPage(Model model,String subtitle){
        Page page = new Page();
        page.setTitle("Users");
        page.setSubtitle(subtitle);
        model.addAttribute("page",page);
        return model;
    }
}
