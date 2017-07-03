package org.woehlke.twitterwall.frontend.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.model.CountedEntities;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitter;

/**
 * Created by tw on 03.07.17.
 */
@Controller
public class ServiceController extends AbstractTwitterwallController {

    private final PersistDataFromTwitter persistDataFromTwitter;

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.context.test}")
    private boolean contextTest;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;

    @Autowired
    public ServiceController(PersistDataFromTwitter persistDataFromTwitter) {
        this.persistDataFromTwitter = persistDataFromTwitter;
    }

    @RequestMapping("/service/count")
    public String countedEntities(Model model) {
        String msg = "/hashtags: ";
        logEnv();
        String title = "HashTags";
        String subtitle = searchterm;
        String symbol = Symbols.DATABASE.toString();
        model = setupPage(model,title,subtitle,symbol);

        CountedEntities countedEntities =this.persistDataFromTwitter.countAll();
        model.addAttribute("countedEntities", countedEntities);
        return "countedEntities";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSet(menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
