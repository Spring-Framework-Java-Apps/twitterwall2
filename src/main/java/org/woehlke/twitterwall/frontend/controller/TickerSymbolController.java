package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.TickerSymbolService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

import javax.persistence.EntityNotFoundException;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/tickersymbol")
public class TickerSymbolController {

    @RequestMapping(path="/all")
    public String getAll(
            @RequestParam(name= "page", defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page,
            Model model
    ){
        String subtitle = "all";
        String title = "TickerSymbol";
        String sortByColumn = "url";
        String symbol = Symbols.DATABASE.toString();
        model =  controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                sortByColumn
        );
        Page<TickerSymbol> myPageContent = tickerSymbolService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "tickersymbol/all";
    }

    @RequestMapping(path="/{id}")
    public String getTickerSymbolById(
            @PathVariable("id") TickerSymbol tickerSymbol,
            @RequestParam(name= "pageTweet" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageTweet,
            @RequestParam(name= "pageUser" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageUser,
            Model model) {
        if(tickerSymbol == null){
            throw new EntityNotFoundException();
        } else {
            String msg = "/tickersymbol/ "+tickerSymbol.getId();
            String title = "TickerSymbol "+tickerSymbol.getUniqueId();
            String subtitle = "List of User and Tweets for one TickerSymbol";
            String symbol = Symbols.MENTION.toString();
            model = controllerHelper.setupPage(model,title,subtitle,symbol);
            Pageable pageRequestTweet = new PageRequest(pageTweet, frontendProperties.getPageSize());
            Pageable pageRequestUser = new PageRequest(pageUser, frontendProperties.getPageSize());
            log.debug(msg+" try to: tweetService.findTweetsForMedia: ");
            Page<Tweet> tweets = tweetService.findTweetsForTickerSymbol(tickerSymbol,pageRequestTweet);
            model.addAttribute("latestTweets", tweets);
            log.debug(msg+" try to: userService.getUsersForMedia: ");
            Page<User> users = userService.getUsersForTickerSymbol(tickerSymbol,pageRequestUser);
            model.addAttribute("users", users);
            model.addAttribute("tickerSymbol", tickerSymbol);
            return "tickersymbol/id";
        }
    }

    private static final Logger log = LoggerFactory.getLogger(TickerSymbolController.class);

    private final FrontendProperties frontendProperties;

    private final TickerSymbolService tickerSymbolService;

    private final UserService userService;

    private final TweetService tweetService;

    private final ControllerHelper controllerHelper;

    @Autowired
    public TickerSymbolController(
            FrontendProperties frontendProperties,
            TickerSymbolService tickerSymbolService,
            UserService userService, TweetService tweetService, ControllerHelper controllerHelper
    ) {
        this.frontendProperties = frontendProperties;
        this.tickerSymbolService = tickerSymbolService;
        this.userService = userService;
        this.tweetService = tweetService;
        this.controllerHelper = controllerHelper;
    }

}
