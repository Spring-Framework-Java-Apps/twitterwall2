package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.service.TweetService;


/**
 * Created by tw on 10.06.17.
 */
@Controller
@RequestMapping("/tweet")
public class TweetController {

    @RequestMapping("/all")
    public String getLatestTweets(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        controllerHelper.logEnv();
        model = controllerHelper.setupPage(model,"Tweets",searchterm,Symbols.HOME.toString());
        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC,"createdAt");
        Page<Tweet> latest = tweetService.getAll(pageRequest);
        model.addAttribute("latestTweets", latest);
        return "tweet/all";
    }

    private static final Logger log = LoggerFactory.getLogger(TweetController.class);

    private final TweetService tweetService;

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Autowired
    public TweetController(TweetService tweetService, ControllerHelper controllerHelper) {
        this.tweetService = tweetService;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;
}
