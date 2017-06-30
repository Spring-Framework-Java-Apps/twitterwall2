package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.model.Page;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.process.tasks.PersistDataFromTwitter;

import javax.persistence.NoResultException;
import java.util.Date;

/**
 * Created by tw on 14.06.17.
 */
@Controller
public class ImprintController {

    private static final Logger log = LoggerFactory.getLogger(ImprintController.class);
    
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

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

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
        log.info("-----------------------------------------");
        logEnv();
        Page page = new Page();
        page.setSymbol("<i class=\"fa fa-university\" aria-hidden=\"true\"></i>");
        page.setMenuAppName(menuAppName);
        page.setShowMenuUsers(showMenuUsers);
        page.setTitle("Impressum");
        page.setSubtitle("www.natural-born-coder.de <br/> twitterwall-port80guru.herokuapp.com");
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        log.info(page.toString());
        model.addAttribute("page", page);
        String screenName = imprintScreenName;
        try {
            log.info("screenName = "+ screenName);
            User user = userService.findByScreenName(screenName);
            log.info("userService.findByScreenName: found User = "+user.toString());
            model.addAttribute("user", user);
            log.info("model.addAttribute user = "+user.toString());
        } catch (EmptyResultDataAccessException e){
            log.info("EmptyResultDataAccessException at userService.findByScreenName for screenName="+screenName);
            TwitterProfile twitterProfile = twitterApiService.getUserProfileForScreenName(screenName);
            log.info("twitterApiService.getUserProfileForScreenName: found TwitterProfile = "+twitterProfile.toString());
            try {
                log.info("try: persistDataFromTwitter.storeUserProfile for twitterProfile = "+twitterProfile.toString());
                User user = persistDataFromTwitter.storeUserProfile(twitterProfile);
                log.info("persistDataFromTwitter.storeUserProfile: stored User = "+user.toString());
                model.addAttribute("user", user);
                log.info("model.addAttribute user = "+user.toString());
            } catch (EmptyResultDataAccessException ex){
                log.warn("persistDataFromTwitter.storeUserProfile raised EmptyResultDataAccessException: "+ex.getMessage());
                User user = getDummyUser();
                model.addAttribute("user", user);
                log.info("model.addAttribute user = "+user.toString());
            } catch (NoResultException exe) {
                log.warn("persistDataFromTwitter.storeUserProfile raised NoResultException: "+exe.getMessage());
                User user = getDummyUser();
                model.addAttribute("user", user);
                log.info("model.addAttribute user = "+user.toString());
            }
        }  finally {
            log.info("... finally done ...");
        }
        log.info("-----------------------------------------");
        return "imprint";
    }

    private User getDummyUser(){
        long idTwitter=0L;
        String screenName = imprintScreenName;
        String name="Exception Handler Dummy Username";
        String url="https://github.com/phasenraum2010/twitterwall2";
        String profileImageUrl="https://avatars2.githubusercontent.com/u/303766?v=3&s=460";
        String description="Exception Handler Dummy Description with some #HashTag an URL like https://thomas-woehlke.blogspot.de/ and an @Mention.";
        String location="Berlin, Germany";
        Date createdDate = new Date();
        User user = new User(idTwitter,screenName, name, url, profileImageUrl, description, location, createdDate);
        return user;
    }

    private void logEnv(){
        log.info("twitterwall.frontend.theme = "+theme);
        log.info("twitterwall.frontend.info.webpage = "+infoWebpage);
        //log.info("twitterwall.backend.twitter.fetchTestData = "+fetchTestData);
        log.info("twitterwall.frontend.menu.users = "+showMenuUsers);
        log.info("twitter.searchQuery = "+searchterm);
        log.info("twitterwall.frontend.menu.appname = "+menuAppName);
        log.info("twitterwall.frontend.imprint.screenName = "+imprintScreenName);
    }
}
