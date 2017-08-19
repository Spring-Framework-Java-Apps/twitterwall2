package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.configuration.properties.TwitterProperties;
import org.woehlke.twitterwall.frontend.content.Symbols;
import org.woehlke.twitterwall.frontend.content.ContentFactory;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping(path="/application")
public class ApplicationController {

    @RequestMapping(path="/management")
    public String managementPage(Model model) {
        String msg = "/application/domain/count: ";
        String title = "Application Management";
        String subtitle = twitterProperties.getSearchQuery();
        String symbol = Symbols.DATABASE.toString();
        model = contentFactory.setupPage(model,title,subtitle,symbol);
        return "application/management";
    }

    private final ContentFactory contentFactory;

    private final TwitterProperties twitterProperties;

    @Autowired
    public ApplicationController(
            ContentFactory contentFactory,
            TwitterProperties twitterProperties) {
        this.contentFactory = contentFactory;
        this.twitterProperties = twitterProperties;
    }

}
