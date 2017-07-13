package org.woehlke.twitterwall.frontend.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.application.parts.CountedEntities;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 03.07.17.
 */
@Controller("/application")
public class DomainController extends AbstractTwitterwallController {

    @RequestMapping("/domain/count")
    public String domainCount(Model model) {
        String msg = "/hashtags: ";
        logEnv();
        String title = "Counted Entities";
        String subtitle = searchterm;
        String symbol = Symbols.DATABASE.toString();
        model = setupPage(model,title,subtitle,symbol);
        CountedEntities countedEntities =this.countedEntitiesService.countAll();
        model.addAttribute("countedEntities", countedEntities);
        return "countedEntities";
    }

    private final CountedEntitiesService countedEntitiesService;

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
    public DomainController(CountedEntitiesService countedEntitiesService) {
        this.countedEntitiesService = countedEntitiesService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSet(menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
