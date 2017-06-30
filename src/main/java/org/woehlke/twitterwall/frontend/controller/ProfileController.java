package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.model.Page;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.exceptions.controller.ControllerRequestParameterSyntaxException;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

/**
 * Created by tw on 14.06.17.
 */
@Controller
public class ProfileController {

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    private final UserService userService;

    private final TweetService tweetService;

    @Autowired
    public ProfileController(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @RequestMapping("/profile/{screenName}")
    public String follower(@PathVariable String screenName, Model model) {
        if (User.isValidScreenName(screenName)) {
            User user = userService.findByScreenName(screenName);
            List<Tweet> tweetsForUser = tweetService.getTweetsForUser(user);
            Page page = new Page();
            page.setSymbol("<i class=\"fa fa-users\" aria-hidden=\"true\"></i>");
            page.setMenuAppName(menuAppName);
            page.setTitle("@" + user.getScreenName());
            page.setSubtitle(user.getName());
            page.setShowMenuUsers(showMenuUsers);
            page.setTwitterSearchTerm(searchterm);
            page.setInfoWebpage(infoWebpage);
            page.setTheme(theme);
            model.addAttribute("page", page);
            model.addAttribute("user", user);
            model.addAttribute("latestTweets",tweetsForUser);
            return "profile";
        } else {
            throw new ControllerRequestParameterSyntaxException("/profile/{screenName}", screenName);
        }
    }
}
