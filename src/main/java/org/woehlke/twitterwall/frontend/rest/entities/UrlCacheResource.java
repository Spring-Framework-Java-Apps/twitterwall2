package org.woehlke.twitterwall.frontend.rest.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.service.UrlCacheService;

import static org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/urlcache")
public class UrlCacheResource {

    @RequestMapping(path="/count", method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
    return this.urlCacheService.count();
    }

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<UrlCache> getAll(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        return this.urlCacheService.getAll(pageRequest);
    }

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    private final UrlCacheService urlCacheService;;

    @Autowired
    public UrlCacheResource(UrlCacheService urlCacheService) {
        this.urlCacheService = urlCacheService;
    }


}
