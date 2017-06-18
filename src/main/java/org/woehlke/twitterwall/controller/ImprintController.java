package org.woehlke.twitterwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;

/**
 * Created by tw on 14.06.17.
 */
@Controller
public class ImprintController {

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    private final UserService userService;

    @Autowired
    public ImprintController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/imprint")
    public String index(Model model) {
        Page page = new Page();
        page.setMenuAppName(menuAppName);
        page.setShowMenuUsers(showMenuUsers);
        page.setTitle("Impressum");
        page.setSubtitle("www.natural-born-coder.de <br/> twitterwall-port80guru.herokuapp.com");
        model.addAttribute("page",page);
        String screenName = "port80guru";
        User user = userService.findByScreenName(screenName);
        model.addAttribute("user", user);
        return "imprint";
    }
}
