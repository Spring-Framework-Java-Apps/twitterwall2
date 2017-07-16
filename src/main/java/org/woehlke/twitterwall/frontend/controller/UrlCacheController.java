package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.service.UrlCacheService;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/urlcache")
public class UrlCacheController  extends AbstractTwitterwallController {

    private final static String PATH="/urlcache";

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page, Model model){
        logEnv();
        String subtitle = "all";
        String title = "UrlCache";
        String symbol = Symbols.DATABASE.toString();
        model = setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC,"url");
        Page<UrlCache> myPageContent = urlCacheService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return PATH+"/all";
    }

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

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

    private final UrlCacheService urlCacheService;

    @Autowired
    public UrlCacheController(UrlCacheService urlCacheService) {
        this.urlCacheService = urlCacheService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSet(menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
