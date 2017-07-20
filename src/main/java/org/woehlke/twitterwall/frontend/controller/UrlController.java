package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.TwitterProperties;
import org.woehlke.twitterwall.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.Url;
import org.woehlke.twitterwall.oodm.service.UrlService;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/url")
public class UrlController {

    private final static String PATH="/url";

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model){
        controllerHelper.logEnv();
        String subtitle = "all";
        String title = "Url";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize(), Sort.Direction.ASC,"url");
        Page<Url> myPageContent = urlService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return PATH+"/all";
    }

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final TwitterProperties twitterProperties;

    private final UrlService urlService;

    private final ControllerHelper controllerHelper;

    @Autowired
    public UrlController(TwitterwallFrontendProperties twitterwallFrontendProperties, TwitterProperties twitterProperties, UrlService urlService, ControllerHelper controllerHelper) {
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.twitterProperties = twitterProperties;
        this.urlService = urlService;
        this.controllerHelper = controllerHelper;
    }
}
