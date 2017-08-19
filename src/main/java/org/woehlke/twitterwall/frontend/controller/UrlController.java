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
import org.woehlke.twitterwall.frontend.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.model.Url;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UrlService;
import org.woehlke.twitterwall.oodm.service.UserService;

import javax.persistence.EntityNotFoundException;

import static org.woehlke.twitterwall.frontend.common.ControllerHelper.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/url")
public class UrlController {


    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model){
        String subtitle = "all";
        String title = "Url";
        String sortByColumn = "url";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(
            page,
            frontendProperties.getPageSize(),
            Sort.Direction.ASC,
            sortByColumn
        );
        Page<Url> myPageContent = urlService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "url/all";
    }

    @RequestMapping(path="/{id}")
    public String getUrlById(
            @PathVariable("id") Url url,
            @RequestParam(name= "pageTweet" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageTweet,
            @RequestParam(name= "pageUser" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageUser,
            Model model) {
        if(url == null){
            throw new EntityNotFoundException();
        } else {
            String msg = "/url/ "+url.getId();
            String title = "Url "+url.getUniqueId();
            String subtitle = "List of User and Tweets for one Url";
            String symbol = Symbols.MENTION.toString();
            model = controllerHelper.setupPage(model,title,subtitle,symbol);
            Pageable pageRequestTweet = new PageRequest(pageTweet, frontendProperties.getPageSize());
            Pageable pageRequestUser = new PageRequest(pageUser, frontendProperties.getPageSize());
            log.debug(msg+" try to: tweetService.findTweetsForMedia: ");
            Page<Tweet> tweets = tweetService.findTweetsForUrl(url,pageRequestTweet);
            model.addAttribute("latestTweets", tweets);
            log.debug(msg+" try to: userService.getUsersForMedia: ");
            Page<User> users = userService.getUsersForUrl(url,pageRequestUser);
            model.addAttribute("users", users);
            model.addAttribute("url", url);
            return "url/id";
        }
    }

    private static final Logger log = LoggerFactory.getLogger(UrlController.class);

    private final FrontendProperties frontendProperties;

    private final UrlService urlService;

    private final UserService userService;

    private final TweetService tweetService;

    private final ControllerHelper controllerHelper;

    @Autowired
    public UrlController(
            FrontendProperties frontendProperties,
            UrlService urlService,
            UserService userService, TweetService tweetService, ControllerHelper controllerHelper
    ) {
        this.frontendProperties = frontendProperties;
        this.urlService = urlService;
        this.userService = userService;
        this.tweetService = tweetService;
        this.controllerHelper = controllerHelper;
    }

}
