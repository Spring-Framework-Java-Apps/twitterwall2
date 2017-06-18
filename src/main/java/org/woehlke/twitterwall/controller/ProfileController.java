package org.woehlke.twitterwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.exceptions.ControllerRequestParameterSyntaxException;
import org.woehlke.twitterwall.oodm.service.UserService;

import javax.xml.ws.http.HTTPException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tw on 14.06.17.
 */
@Controller
public class ProfileController {

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/profile/{screenName}")
    public String follower(@PathVariable String screenName, Model model) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_]{1,15}$");
        Matcher m = p.matcher(screenName);
        if(m.matches()){
            User user = userService.findByScreenName(screenName);
            Page page = new Page();
            page.setMenuAppName(menuAppName);
            page.setTitle("@"+ user.getScreenName());
            page.setSubtitle(user.getName());
            page.setShowMenuUsers(showMenuUsers);
            model.addAttribute("page",page);
            model.addAttribute("user", user);
            return "profile";
        } else {
            throw new ControllerRequestParameterSyntaxException("/profile/{screenName}",screenName);
        }
    }
}
