package org.woehlke.twitterwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.entities.MyTwitterProfile;
import org.woehlke.twitterwall.oodm.service.MyTwitterProfileService;

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

    private final MyTwitterProfileService myTwitterProfileService;

    @Autowired
    public ProfileController(MyTwitterProfileService myTwitterProfileService) {
        this.myTwitterProfileService = myTwitterProfileService;
    }

    @RequestMapping("/profile/{screenName}")
    public String follower(@PathVariable String screenName, Model model) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_]{1,15}$");
        Matcher m = p.matcher(screenName);
        if(m.matches()){
            MyTwitterProfile myTwitterProfile = myTwitterProfileService.findByScreenName(screenName);
            Page page = new Page();
            page.setMenuAppName(menuAppName);
            page.setTitle("@"+myTwitterProfile.getScreenName());
            page.setSubtitle(myTwitterProfile.getName());
            model.addAttribute("page",page);
            model.addAttribute("user",myTwitterProfile);
            return "user/profile";
        } else {
            int statusCode = 404;
            throw new HTTPException(statusCode);
        }
    }
}
