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
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.service.UrlCacheService;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/urlcache")
public class UrlCacheController {

    private final static String PATH="urlcache";

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model){
        String subtitle = "all";
        String title = "UrlCache";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize(), Sort.Direction.ASC,"url");
        Page<UrlCache> myPageContent = urlCacheService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return PATH+"/all";
    }

    private final ControllerHelper controllerHelper;

    private final FrontendProperties frontendProperties;

    private final UrlCacheService urlCacheService;

    @Autowired
    public UrlCacheController(UrlCacheService urlCacheService, ControllerHelper controllerHelper, FrontendProperties frontendProperties, TwitterProperties twitterProperties) {
        this.urlCacheService = urlCacheService;
        this.controllerHelper = controllerHelper;
        this.frontendProperties = frontendProperties;
    }
}
