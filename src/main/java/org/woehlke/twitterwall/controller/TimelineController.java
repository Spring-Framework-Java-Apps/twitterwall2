package org.woehlke.twitterwall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.oodm.service.MyTweetService;

/**
 * Created by tw on 10.06.17.
 */
@Controller
public class TimelineController {

    private static final Logger log = LoggerFactory.getLogger(TimelineController.class);

    private final MyTweetService myTweetService;

    @Autowired
    public TimelineController(MyTweetService myTweetService) {
        this.myTweetService = myTweetService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("latestTweets",myTweetService.getLatestTweets());
        return "timeline";
    }

}
