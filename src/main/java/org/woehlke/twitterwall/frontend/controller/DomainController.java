package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.conf.TwitterProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.parts.CountedEntities;
import org.woehlke.twitterwall.scheduled.service.persist.CountedEntitiesService;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping(path="/application")
public class DomainController {

    @RequestMapping(path="/domain/count")
    public String domainCount(Model model) {
        String msg = "/application/domain/count: ";
        controllerHelper.logEnv();
        String title = "Counted Entities";
        String subtitle = twitterProperties.getSearchQuery();
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        CountedEntities countedEntities =this.countedEntitiesService.countAll();
        model.addAttribute("countedEntities", countedEntities);
        return "/application/domain/count";
    }

    private final CountedEntitiesService countedEntitiesService;

    @Autowired
    public DomainController(CountedEntitiesService countedEntitiesService, ControllerHelper controllerHelper, TwitterProperties twitterProperties) {
        this.countedEntitiesService = countedEntitiesService;
        this.controllerHelper = controllerHelper;
        this.twitterProperties = twitterProperties;
    }

    private final ControllerHelper controllerHelper;

    private final TwitterProperties twitterProperties;

}
