package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;

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
    Page<Tweet> getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        return this.tweetService.getAll(pageRequest);
    }

    @RequestMapping(path="/latest", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<Tweet> getLatestTweets(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize(), Sort.Direction.DESC,"createdAt");
        return this.tweetService.getAll(pageRequest);
    }

    private final TweetService tweetService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public TweetResource(TweetService tweetService, FrontendProperties frontendProperties) {
    this.tweetService = tweetService;
        this.frontendProperties = frontendProperties;
    }

}
