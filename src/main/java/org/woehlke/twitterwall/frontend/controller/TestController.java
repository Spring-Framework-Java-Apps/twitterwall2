package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.oodm.service.application.TaskService;
import org.woehlke.twitterwall.scheduled.service.backend.TwitterApiService;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.scheduled.service.facade.FetchUsersFromDefinedUserList;
import org.woehlke.twitterwall.scheduled.service.persist.StoreOneTweet;
import org.woehlke.twitterwall.scheduled.service.persist.StoreUserProfile;

import java.util.List;


/**
 * Created by tw on 30.06.17.
 */
@Controller
public class TestController extends AbstractTwitterwallController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private final TwitterApiService twitterApiService;

    private final StoreOneTweet storeOneTweet;

    private final StoreUserProfile storeUserProfile;

    private final UserService userService;

    private final TaskService taskService;

    private final FetchUsersFromDefinedUserList fetchUsersFromDefinedUserList;


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
    public TestController(TwitterApiService twitterApiService, StoreOneTweet storeOneTweet, StoreUserProfile storeUserProfile, UserService userService, TaskService taskService, FetchUsersFromDefinedUserList fetchUsersFromDefinedUserList) {
        this.twitterApiService = twitterApiService;
        this.storeOneTweet = storeOneTweet;
        this.storeUserProfile = storeUserProfile;
        this.userService = userService;
        this.taskService = taskService;
        this.fetchUsersFromDefinedUserList = fetchUsersFromDefinedUserList;
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
        super.setupAfterPropertiesSetWithTesting(taskService,twitterApiService,storeOneTweet,storeUserProfile,userService,menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }

    @Async
    private void startOnListRenew(){
        String msg = "startOnListRenew: ";
        log.info(msg+"START scheduledTasksFacade.fetchUsersFromDefinedUserList: ");
        fetchUsersFromDefinedUserList.fetchUsersFromDefinedUserList();
        log.info(msg+"DONE scheduledTasksFacade.fetchUsersFromDefinedUserList: ");
    }
}
