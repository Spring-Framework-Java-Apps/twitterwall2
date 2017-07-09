package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitter;
import org.woehlke.twitterwall.scheduled.ScheduledTasksFacade;

import java.util.List;


/**
 * Created by tw on 30.06.17.
 */
@Controller
public class TestController extends AbstractTwitterwallController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private final TwitterApiService twitterApiService;

    private final PersistDataFromTwitter persistDataFromTwitter;

    private final ScheduledTasksFacade scheduledTasksFacade;

    private final UserService userService;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Value("${twitterwall.context.test}")
    private boolean contextTest;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;

    @Autowired
    public TestController(TwitterApiService twitterApiService, PersistDataFromTwitter persistDataFromTwitter, ScheduledTasksFacade scheduledTasksFacade, UserService userService) {
        this.twitterApiService = twitterApiService;
        this.persistDataFromTwitter = persistDataFromTwitter;
        this.scheduledTasksFacade = scheduledTasksFacade;
        this.userService = userService;
    }

    @RequestMapping("/getTestData")
    public String getTestData(Model model) {
        logEnv();
        model = super.setupPage(model,"Test Data Tweets",searchterm,Symbols.GET_TEST_DATA.toString());
        String msg = "/getTestData : ";
        if(contextTest){
            super.getTestDataTweets(msg,model);
            super.getTestDataUser(msg,model);
        }
        return "timeline";
    }

    @RequestMapping("/user/onlist/renew")
    public String getOnListRenew(Model model) {
        String msg = "getOnListRenew: ";
        startOnListRenew();
        log.info(msg+"START userService.getOnList(): ");
        List<User> usersOnList = userService.getOnList();
        log.info(msg+"DONE userService.getOnList(): ");
        model.addAttribute("users", usersOnList);
        String symbol = Symbols.LEAF.toString();
        String title = "Renew List of Users On List";
        model = setupPage(model, title, "Users", symbol);
        return "user";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSetWithTesting(twitterApiService,persistDataFromTwitter,menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }

    @Async
    private void startOnListRenew(){
        String msg = "startOnListRenew: ";
        log.info(msg+"START scheduledTasksFacade.fetchUsersFromDefinedUserList: ");
        scheduledTasksFacade.fetchUsersFromDefinedUserList();
        log.info(msg+"DONE scheduledTasksFacade.fetchUsersFromDefinedUserList: ");
    }
}
