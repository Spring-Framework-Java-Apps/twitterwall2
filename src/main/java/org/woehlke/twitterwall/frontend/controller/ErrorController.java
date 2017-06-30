package org.woehlke.twitterwall.frontend.controller;

/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.model.Page;
*/


/**
 * Created by tw on 30.06.17.
 */
//@Controller
public class ErrorController {

    /*
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);
    
    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitterwall.backend.twitter.fetchTestData}")
    private boolean fetchTestData;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;


    @RequestMapping("/error")
    public String hashTags(Model model) {
        logEnv();
        Page page = new Page();
        page.setSymbol("<i class=\"fa fa-hashtag\" aria-hidden=\"true\"></i>");
        page.setMenuAppName(menuAppName);
        page.setTitle("Error");
        page.setSubtitle(searchterm);
        page.setShowMenuUsers(showMenuUsers);
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        model.addAttribute("page", page);
        return "tags";
    }

    private void logEnv(){
        log.info("twitterwall.frontend.theme = "+theme);
        log.info("twitterwall.frontend.info.webpage = "+infoWebpage);
        log.info("twitterwall.backend.twitter.fetchTestData = "+fetchTestData);
        log.info("twitterwall.frontend.menu.users = "+showMenuUsers);
        log.info("twitter.searchQuery = "+searchterm);
        log.info("twitterwall.frontend.menu.appname = "+menuAppName);
    }
    */
}
