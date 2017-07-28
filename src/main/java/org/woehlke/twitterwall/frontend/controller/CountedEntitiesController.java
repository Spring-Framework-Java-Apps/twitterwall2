package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.*;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;
import org.woehlke.twitterwall.oodm.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping(path="/application/countedEntities")
public class CountedEntitiesController {

    private final static String PATH="application/countedEntities";

    @RequestMapping(path="/tweet/hashtag")
    public String domainCountTweet2hashtag(
            @RequestParam(
                    name= "page",
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "Tweet -&gt; HashTag";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2HashTag(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            Tweet tweet = tweetService.findById(object2Entity.getObjectId());
            HashTag hashTag = hashTagService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(tweet.getUser().getScreenName());
            object2Entity.setEntityInfo(hashTag.getText());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("nameObject", "tweet");
        model.addAttribute("nameEntity", "hashtag");
        return PATH;
    }

    @RequestMapping(path="/tweet/media")
    public String domainCountTweet2media(
            @RequestParam(
                    name= "page",
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "Tweet -&gt; Media";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2Media(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            Tweet tweet = tweetService.findById(object2Entity.getObjectId());
            Media medium = mediaService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(tweet.getUser().getScreenName());
            object2Entity.setEntityInfo(medium.getUrl());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("nameObject", "tweet");
        model.addAttribute("nameEntity", "media");
        return PATH;
    }

    @RequestMapping(path="/tweet/mention")
    public String domainCountTweet2mention(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "Tweet -&gt; Mention";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2Mention(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            Tweet tweet = tweetService.findById(object2Entity.getObjectId());
            Mention mention = mentionService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(tweet.getUser().getScreenName());
            object2Entity.setEntityInfo(mention.getScreenName());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("nameObject", "tweet");
        model.addAttribute("nameEntity", "mention");
        return PATH;
    }

    @RequestMapping(path="/tweet/tickersymbol")
    public String domainCountTweet2tickersymbol(
            @RequestParam(
                    name= "page",
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "Tweet -&gt; TickerSymbol";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2TickerSymbol(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            Tweet tweet = tweetService.findById(object2Entity.getObjectId());
            TickerSymbol tickerSymbol = tickerSymbolService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(tweet.getUser().getScreenName());
            object2Entity.setEntityInfo(tickerSymbol.getUniqueId());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("nameObject", "tweet");
        model.addAttribute("nameEntity", "tickersymbol");
        return PATH;
    }

    @RequestMapping(path="/tweet/url")
    public String domainCountTweet2url(
            @RequestParam(
                    name= "page",
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "Tweet -&gt; Url";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2Url(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            Tweet tweet = tweetService.findById(object2Entity.getObjectId());
            Url url = urlService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(tweet.getUser().getScreenName());
            object2Entity.setEntityInfo(url.getExpanded());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("nameObject", "tweet");
        model.addAttribute("nameEntity", "url");
        return PATH;
    }

    @RequestMapping(path="/user/hashtag")
    public String domainCountUserprofile2hashtag(
            @RequestParam(
                    name= "page" ,
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "UserProfile -&gt; HashTag";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2HashTag(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            User user = userService.findById(object2Entity.getObjectId());
            HashTag hashTag = hashTagService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(user.getScreenName());
            object2Entity.setEntityInfo(hashTag.getText());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("nameObject", "user");
        model.addAttribute("nameEntity", "hashtag");
        return PATH;
    }

    @RequestMapping(path="/user/media")
    public String domainCountUserprofile2media(
            @RequestParam(
                    name= "page" ,
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "UserProfile -&gt; Media";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2Media(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            User user = userService.findById(object2Entity.getObjectId());
            Media medium = mediaService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(user.getScreenName());
            object2Entity.setEntityInfo(medium.getUrl());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("nameObject", "user");
        model.addAttribute("nameEntity", "media");
        return PATH;
    }

    @RequestMapping(path="/user/mention")
    public String domainCountUserprofile2mention(
            @RequestParam(
                name= "page",
                defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "UserProfile -&gt; Mention";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2Mentiong(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            User user = userService.findById(object2Entity.getObjectId());
            Mention mention = mentionService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(user.getScreenName());
            object2Entity.setEntityInfo(mention.getScreenName());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("nameObject", "user");
        model.addAttribute("nameEntity", "mention");
        return PATH;
    }

    @RequestMapping(path="/user/tickersymbol")
    public String domainCountUserprofile2Tickersymbol(
            @RequestParam(
                    name= "page",
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "UserProfile -&gt; TickerSymbol";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2TickerSymbol(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            User user = userService.findById(object2Entity.getObjectId());
            TickerSymbol tickerSymbol = tickerSymbolService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(user.getScreenName());
            object2Entity.setEntityInfo(tickerSymbol.getUniqueId());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("nameObject", "user");
        model.addAttribute("nameEntity", "tickersymbol");
        return PATH;
    }

    @RequestMapping(path="/user/url")
    public String domainCountUserprofile2Url(
            @RequestParam(
                    name= "page",
                    defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER
            ) int page, Model model
    ) {
        String title = "UserProfile -&gt; Url";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2Url(pageRequest);
        List<Object2Entity> listObject2EntityContent = new ArrayList();
        for(Object2Entity object2Entity:listObject2Entity.getContent()){
            User user = userService.findById(object2Entity.getObjectId());
            Url url = urlService.findById(object2Entity.getEntityId());
            object2Entity.setObjectInfo(user.getScreenName());
            object2Entity.setEntityInfo(url.getExpanded());
            listObject2EntityContent.add(object2Entity);
        }
        model.addAttribute("listObject2Entity", listObject2Entity);
        model.addAttribute("listObject2EntityContent",listObject2EntityContent);
        model.addAttribute("nameObject", "user");
        model.addAttribute("nameEntity", "url");
        return PATH;
    }

    private void setUpThisPage(String title,Model model){
        String subtitle = "Counted Entities";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
    }


    @Autowired
    public CountedEntitiesController(FrontendProperties frontendProperties, ControllerHelper controllerHelper, TweetService tweetService, UserService userService, HashTagService hashTagService, MediaService mediaService, MentionService mentionService, TickerSymbolService tickerSymbolService, UrlService urlService) {
        this.frontendProperties = frontendProperties;
        this.controllerHelper = controllerHelper;
        this.tweetService = tweetService;
        this.userService = userService;
        this.hashTagService = hashTagService;
        this.mediaService = mediaService;
        this.mentionService = mentionService;
        this.tickerSymbolService = tickerSymbolService;
        this.urlService = urlService;
    }



    private final FrontendProperties frontendProperties;

    private final ControllerHelper controllerHelper;

    private final TweetService tweetService;

    private final UserService userService;

    private final HashTagService hashTagService;

    private final MediaService mediaService;

    private final MentionService mentionService;

    private final TickerSymbolService tickerSymbolService;

    private final UrlService urlService;
}
