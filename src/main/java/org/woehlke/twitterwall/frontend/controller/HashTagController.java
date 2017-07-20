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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.model.HashTagCounted;
import org.woehlke.twitterwall.oodm.entities.Tweet;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;
import org.woehlke.twitterwall.oodm.service.HashTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.woehlke.twitterwall.frontend.controller.common.ControllerHelper.FIRST_PAGE_NUMBER;
import static org.woehlke.twitterwall.oodm.entities.HashTag.HASHTAG_TEXT_PATTERN;

/**
 * Created by tw on 12.07.17.
 */
@Controller
@RequestMapping(path="/hashtag")
public class HashTagController {

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ FIRST_PAGE_NUMBER) int page, Model model){
        controllerHelper.logEnv();
        String subtitle = "all";
        String title = "HashTag";
        String symbol = Symbols.HASHTAG.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC,"text");
        Page<HashTag> myPageContent = hashTagService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "/hashtag/all";
    }

    @RequestMapping(path="/{text}")
    public String hashTagFromTweetsAndUsers(
        @PathVariable("text") String text,
        @RequestParam(name= "pageTweet" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageTweet,
        @RequestParam(name= "pageUser" ,defaultValue=""+ FIRST_PAGE_NUMBER) int pageUser,
         Model model)
    {
        String msg = "/hashtag/" + text + " ";
        controllerHelper.logEnv();
        Pattern p = Pattern.compile(HASHTAG_TEXT_PATTERN);
        Matcher m = p.matcher(text);
        if (m.matches()) {
            String msg2 = msg + " parameter IS valid - START ";
            log.debug(msg2);
            Pageable pageRequestTweet = new PageRequest(pageTweet, pageSize);
            Pageable pageRequestUser = new PageRequest(pageUser, pageSize);
            String subtitle = "Tweets und User für HashTag";
            String title = text;
            String symbol = Symbols.HASHTAG.toString();
            model = controllerHelper.setupPage(model, title, subtitle, symbol);
            log.debug(msg + " try to: hashTagService.findByText ");
            HashTag hashTag = hashTagService.findByText(text);
            model.addAttribute("hashTag",hashTag);
            log.debug(msg + " found:  " + text);
            //
            log.debug(msg+" try to: tweetService.findTweetsForHashTag: ");
            Page<Tweet> tweets = tweetService.findTweetsForHashTag(hashTag,pageRequestTweet);
            model.addAttribute("latestTweets", tweets);
            //
            log.debug(msg+" try to: userService.getUsersForHashTag: ");
            Page<User> users = userService.getUsersForHashTag(hashTag,pageRequestUser);
            model.addAttribute("users", users);
            //
            log.debug(msg + " READY - DONE");
            return "/hashtag/hashtagText";
        } else {
            String msg2 = msg + " parameter ist NOT valid";
            log.warn(msg2);
            return "/hashtag/hashtagText";
        }
    }

    @RequestMapping(path="/overview")
    public String hashTagsOverview(Model model) {
        String msg = "/hashtag/overview ";
        controllerHelper.logEnv();
        String title = "HashTags";
        String subtitle = searchterm;
        String symbol = Symbols.HASHTAG.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        List<HashTagCounted> hashTagsTweets = new ArrayList<>();
        List<HashTagCounted> hashTagsUsers = new ArrayList<>();
        Pageable pageRequest = new PageRequest(FIRST_PAGE_NUMBER, pageSize);
        boolean hasNext = true;
        while(hasNext) {
            Page<HashTag> myPage = hashTagService.getAll(pageRequest);
            for (HashTag hashTag : myPage.getContent()) {
                Pageable pageRequestTeets = new PageRequest(0, 1);
                Page<Tweet> tweets = tweetService.findTweetsForHashTag(hashTag, pageRequestTeets);
                String myMSg = msg + " tweetService.findTweetsForHashTag= " + hashTag.getText();
                if (tweets == null) {
                    log.debug(myMSg + " result: null");
                } else {
                    long numberTweets = tweets.getTotalElements();
                    log.debug(myMSg + " result: numberTweets=" + numberTweets);
                    if (numberTweets > 0) {
                        HashTagCounted c = new HashTagCounted(numberTweets, hashTag.getText());
                        hashTagsTweets.add(c);
                    }
                }
                Pageable pageRequestUsers = new PageRequest(0, 1);
                Page<User> users = userService.getUsersForHashTag(hashTag, pageRequestUsers);
                myMSg = msg + " userService.getUsersForHashTag= " + hashTag.getText();
                if (users == null) {
                    log.debug(myMSg + " result: null");
                } else {
                    long numberUsers = users.getTotalElements(); //userService.countUsersForHashTag(hashTag.getText());
                    log.debug(myMSg + " result: numberUsers=" + numberUsers);
                    if (numberUsers > 0) {
                        HashTagCounted c = new HashTagCounted(numberUsers, hashTag.getText());
                        hashTagsUsers.add(c);
                    }
                }
            }
            hasNext = myPage.hasNext();
            if(hasNext){
                pageRequest=myPage.nextPageable();
            }
        }
        model.addAttribute("hashTagsTweets", hashTagsTweets);
        model.addAttribute("hashTagsUsers", hashTagsUsers);
        return "/hashtag/overview";
    }

    private static final Logger log = LoggerFactory.getLogger(HashTagController.class);

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    private final HashTagService hashTagService;

    private final TweetService tweetService;

    private final UserService userService;

    @Autowired
    public HashTagController(HashTagService hashTagService, TweetService tweetService, UserService userService, ControllerHelper controllerHelper) {
        this.hashTagService = hashTagService;
        this.tweetService = tweetService;
        this.userService = userService;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;
}