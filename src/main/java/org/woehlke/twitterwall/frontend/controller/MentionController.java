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
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.content.Symbols;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.oodm.model.Mention;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.MentionService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

import javax.persistence.EntityNotFoundException;

import static org.woehlke.twitterwall.frontend.content.ContentFactory.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/mention")
public class MentionController {

    @RequestMapping(path="/all")
    public String getAll(
        @RequestParam(name= "page" ,defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        String subtitle = "all";
        String title = "Mention";
        String symbol = Symbols.DATABASE.toString();
        model = contentFactory.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(
                page, frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "screenName"
        );
        Page<Mention> myPageContent = mentionService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "mention/all";
    }

    @RequestMapping(path="/{id}")
    public String getMentionById(
            @PathVariable("id") Mention mention,
            @RequestParam(name= "pageTweet" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageTweet,
            @RequestParam(name= "pageUser" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageUser,
            Model model) {
        if(mention == null){
            throw new EntityNotFoundException();
        } else {
            String msg = "/mention/ "+mention.getId();
            String title = "Mention "+mention.getUniqueId();
            String subtitle = "List of User and Tweets for one Mention";
            String symbol = Symbols.MENTION.toString();
            model = contentFactory.setupPage(model,title,subtitle,symbol);
            Pageable pageRequestTweet = new PageRequest(pageTweet, frontendProperties.getPageSize());
            Pageable pageRequestUser = new PageRequest(pageUser, frontendProperties.getPageSize());
            log.debug(msg+" try to: tweetService.findTweetsForMedia: ");
            Page<Tweet> tweets = tweetService.findTweetsForMention(mention,pageRequestTweet);
            model.addAttribute("latestTweets", tweets);
            log.debug(msg+" try to: userService.getUsersForMedia: ");
            Page<User> users = userService.getUsersForMention(mention,pageRequestUser);
            model.addAttribute("users", users);
            model.addAttribute("mention", mention);
            return "mention/id";
        }
    }

    private static final Logger log = LoggerFactory.getLogger(MentionController.class);

    private final FrontendProperties frontendProperties;

    private final MentionService mentionService;

    private final UserService userService;

    private final TweetService tweetService;

    private final ContentFactory contentFactory;

    @Autowired
    public MentionController(
            FrontendProperties frontendProperties,
            MentionService mentionService,
            UserService userService, TweetService tweetService, ContentFactory contentFactory) {
        this.frontendProperties = frontendProperties;
        this.mentionService = mentionService;
        this.userService = userService;
        this.tweetService = tweetService;
        this.contentFactory = contentFactory;
    }

}
