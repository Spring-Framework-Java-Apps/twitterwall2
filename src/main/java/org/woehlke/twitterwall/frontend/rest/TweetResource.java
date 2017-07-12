package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;

import java.util.List;

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

    @RequestMapping(path="/all",method= RequestMethod.GET)
    public @ResponseBody
    List<Tweet> getAll() {
        return this.tweetService.getAll();
    }

    @RequestMapping(path="/latest",method= RequestMethod.GET)
    public @ResponseBody
    List<Tweet> getLatestTweets() {
        return this.tweetService.getLatestTweets();
    }

    private final TweetService tweetService;

    @Autowired
    public TweetResource(TweetService tweetService) {
    this.tweetService = tweetService;
    }

}
