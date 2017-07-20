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
import org.woehlke.twitterwall.ConfigTwitterwall;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.TickerSymbolService;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/tickersymbol")
public class TickerSymbolController {

    private final static String PATH="/tickersymbol";

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model){
        controllerHelper.logEnv();
        String subtitle = "all";
        String title = "TickerSymbol";
        String symbol = Symbols.DATABASE.toString();
        model =  controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, configTwitterwall.getFrontend().getPageSize(), Sort.Direction.ASC,"url");
        Page<TickerSymbol> myPageContent = tickerSymbolService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return PATH+"/all";
    }


    //@Value("${twitterwall.frontend.maxResults}")
    //private int pageSize;

    private final ConfigTwitterwall configTwitterwall;

    private final TickerSymbolService tickerSymbolService;

    @Autowired
    public TickerSymbolController(ConfigTwitterwall configTwitterwall, TickerSymbolService tickerSymbolService, ControllerHelper controllerHelper) {
        this.configTwitterwall = configTwitterwall;
        this.tickerSymbolService = tickerSymbolService;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;
}
