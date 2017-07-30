package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.UrlCache;
import org.woehlke.twitterwall.oodm.service.UrlCacheService;

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
    Page<UrlCache> getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        return this.urlCacheService.getAll(pageRequest);
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public @ResponseBody
    UrlCache findById(
        @PathVariable("id") UrlCache urlCache
    ) {
        return urlCache;
    }

    private final UrlCacheService urlCacheService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public UrlCacheResource(UrlCacheService urlCacheService, FrontendProperties frontendProperties) {
        this.urlCacheService = urlCacheService;
        this.frontendProperties = frontendProperties;
    }


}
