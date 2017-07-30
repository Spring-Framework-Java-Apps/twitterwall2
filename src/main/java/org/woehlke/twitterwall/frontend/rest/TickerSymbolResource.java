package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.TickerSymbolService;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/tickersymbol")
public class TickerSymbolResource {

    @RequestMapping(path="/count", method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
    return this.tickerSymbolService.count();
    }

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<TickerSymbol> getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        return this.tickerSymbolService.getAll(pageRequest);
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public @ResponseBody
    TickerSymbol findById(
            @PathVariable("id") TickerSymbol tickerSymbol
    ) {
        return tickerSymbol;
    }

    private final TickerSymbolService tickerSymbolService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public TickerSymbolResource(TickerSymbolService tickerSymbolService, FrontendProperties frontendProperties) {
        this.tickerSymbolService = tickerSymbolService;
        this.frontendProperties = frontendProperties;
    }
}
