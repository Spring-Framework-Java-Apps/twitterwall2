package org.woehlke.twitterwall.frontend.rest;

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
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;

import static org.woehlke.twitterwall.frontend.controller.common.AbstractTwitterwallController.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/tweet")
public class TweetResource {

    @RequestMapping(path="/count",method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
        return this.tweetService.count();
    }

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<Tweet> getAll(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        return this.tweetService.getAll(pageRequest);
    }

    @RequestMapping(path="/latest", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<Tweet> getLatestTweets(@RequestParam(name= "page" ,defaultValue=""+FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        return this.tweetService.getLatestTweets(pageRequest);
    }

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    private final TweetService tweetService;

    @Autowired
    public TweetResource(TweetService tweetService) {
    this.tweetService = tweetService;
    }

}
