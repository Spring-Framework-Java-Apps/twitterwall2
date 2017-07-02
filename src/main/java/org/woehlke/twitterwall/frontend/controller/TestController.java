package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitter;


/**
 * Created by tw on 30.06.17.
 */
@Controller
public class TestController extends AbstractTwitterwallController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    
    private final TwitterApiService twitterApiService;

    private final PersistDataFromTwitter persistDataFromTwitter;

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
    
    @Autowired
    public TestController(TwitterApiService twitterApiService, PersistDataFromTwitter persistDataFromTwitter) {
        this.twitterApiService = twitterApiService;
        this.persistDataFromTwitter = persistDataFromTwitter;
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
    
    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSetWithTesting(twitterApiService,persistDataFromTwitter,menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName);
    }
}
