package org.woehlke.twitterwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;
import org.woehlke.twitterwall.oodm.service.MyTwitterProfileService;

/**
 * Created by tw on 14.06.17.
 */
@Controller
public class ImprintController {



    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    private final MyTwitterProfileService myTwitterProfileService;

    @Autowired
    public ImprintController(MyTwitterProfileService myTwitterProfileService) {
        this.myTwitterProfileService = myTwitterProfileService;
    }

    @RequestMapping("/imprint")
    public String index(Model model) {
        Page page = new Page();
        page.setMenuAppName(menuAppName);
        page.setTitle("Impressum");
        page.setSubtitle("www.natural-born-coder.de <br/> twitterwall-port80guru.herokuapp.com");
        model.addAttribute("page",page);
        String screenName = "port80guru";
        MyTwitterProfile myTwitterProfile = myTwitterProfileService.findByScreenName(screenName);
        model.addAttribute("user",myTwitterProfile);
        return "imprint";
    }
}
