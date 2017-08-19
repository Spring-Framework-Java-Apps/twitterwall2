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
import org.woehlke.twitterwall.frontend.content.Symbols;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.oodm.model.Media;
import org.woehlke.twitterwall.oodm.model.Tweet;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.oodm.service.MediaService;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

import javax.persistence.EntityNotFoundException;

import static org.woehlke.twitterwall.frontend.content.ContentFactory.FIRST_PAGE_NUMBER;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/media")
public class MediaController {


    @RequestMapping(path="/all")
    public String getAll(
        @RequestParam(name= "page" ,defaultValue=""+ ContentFactory.FIRST_PAGE_NUMBER) int page,
        Model model
    ){
        String subtitle = "all";
        String title = "Media";
        String symbol = Symbols.DATABASE.toString();
        model = contentFactory.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(
                page,
                frontendProperties.getPageSize(),
                Sort.Direction.ASC,
                "url"
        );
        Page<Media> myPageContent = mediaService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "media/all";
    }

    @RequestMapping(path="/{id}")
    public String getMediaById(
            @PathVariable("id") Media media,
            @RequestParam(name= "pageTweet" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageTweet,
            @RequestParam(name= "pageUser" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageUser,
            Model model) {
        if(media == null){
            throw new EntityNotFoundException();
        } else {
            String msg = "/media/ "+media.getId();
            String title = "Media "+media.getUniqueId();
            String subtitle = "List of User and Tweets for one Media";
            String symbol = Symbols.MEDIA.toString();
            model = contentFactory.setupPage(model,title,subtitle,symbol);
            Pageable pageRequestTweet = new PageRequest(pageTweet, frontendProperties.getPageSize());
            Pageable pageRequestUser = new PageRequest(pageUser, frontendProperties.getPageSize());
            log.debug(msg+" try to: tweetService.findTweetsForMedia: ");
            Page<Tweet> tweets = tweetService.findTweetsForMedia(media,pageRequestTweet);
            model.addAttribute("latestTweets", tweets);
            log.debug(msg+" try to: userService.getUsersForMedia: ");
            Page<User> users = userService.getUsersForMedia(media,pageRequestUser);
            model.addAttribute("users", users);
            model.addAttribute("media", media);
            return "media/id";
        }
    }

    private static final Logger log = LoggerFactory.getLogger(MediaController.class);

    private final MediaService mediaService;

    private final UserService userService;

    private final TweetService tweetService;

    private final FrontendProperties frontendProperties;

    private final ContentFactory contentFactory;

    @Autowired
    public MediaController(
            MediaService mediaService,
            UserService userService, TweetService tweetService, FrontendProperties frontendProperties,
            ContentFactory contentFactory
    ) {
        this.mediaService = mediaService;
        this.userService = userService;
        this.tweetService = tweetService;
        this.frontendProperties = frontendProperties;
        this.contentFactory = contentFactory;
    }

}
