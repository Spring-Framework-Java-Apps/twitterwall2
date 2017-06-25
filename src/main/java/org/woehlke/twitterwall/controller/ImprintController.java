package org.woehlke.twitterwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.exceptions.oodm.FindUserByScreenNameException;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.process.backend.TwitterApiService;
import org.woehlke.twitterwall.process.tasks.PersistDataFromTwitter;

/**
 * Created by tw on 14.06.17.
 */
@Controller
public class ImprintController {

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.theme}")
    private String theme;

    private final UserService userService;

    private final TwitterApiService twitterApiService;

    private final PersistDataFromTwitter persistDataFromTwitter;

    @Autowired
    public ImprintController(UserService userService, TwitterApiService twitterApiService, PersistDataFromTwitter persistDataFromTwitter) {
        this.userService = userService;
        this.twitterApiService = twitterApiService;
        this.persistDataFromTwitter = persistDataFromTwitter;
    }

    @RequestMapping("/imprint")
    public String index(Model model) {
        Page page = new Page();
        page.setSymbol("<i class=\"fa fa-university\" aria-hidden=\"true\"></i>");
        page.setMenuAppName(menuAppName);
        page.setShowMenuUsers(showMenuUsers);
        page.setTitle("Impressum");
        page.setSubtitle("www.natural-born-coder.de <br/> twitterwall-port80guru.herokuapp.com");
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        model.addAttribute("page", page);
        String screenName = "port80guru";
        try {
            User user = userService.findByScreenName(screenName);
            model.addAttribute("user", user);
        } catch (FindUserByScreenNameException e){
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
            User user = persistDataFromTwitter.storeUserProfile(twitterProfile);
            model.addAttribute("user", user);
        }
        return "imprint";
    }
}
