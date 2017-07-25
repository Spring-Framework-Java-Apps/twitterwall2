package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.transients.Object2Entity;
import org.woehlke.twitterwall.oodm.service.TweetService;
import org.woehlke.twitterwall.oodm.service.UserService;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping(path="/application/countedEntities")
public class CountedEntitiesController {

    private final static String PATH="application/countedEntities";

    @RequestMapping(path="/tweet/hashtag")
    public String domainCountTweet2hashtag(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "Tweet -&gt; HashTag";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2HashTag(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/tweet/hashtag";
    }

    @RequestMapping(path="/tweet/media")
    public String domainCountTweet2media(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "Tweet -&gt; Media";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2Media(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/tweet/media";
    }

    @RequestMapping(path="/tweet/mention")
    public String domainCountTweet2mention(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "Tweet -&gt; Mention";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2Mention(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/tweet/mention";
    }

    @RequestMapping(path="/tweet/tickersymbol")
    public String domainCountTweet2tickersymbol(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "Tweet -&gt; TickerSymbol";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2TickerSymbol(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/tweet/tickersymbol";
    }

    @RequestMapping(path="/tweet/url")
    public String domainCountTweet2url(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "Tweet -&gt; Url";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = tweetService.findAllTweet2Url(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/tweet/url";
    }

    @RequestMapping(path="/userprofile/hashtag")
    public String domainCountUserprofile2hashtag(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "UserProfile -&gt; HashTag";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2HashTag(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/userprofile/hashtag";
    }

    @RequestMapping(path="/userprofil/media")
    public String domainCountUserprofile2media(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "UserProfile -&gt; Media";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2Media(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/userprofile/media";
    }

    @RequestMapping(path="/userprofile/mention")
    public String domainCountUserprofile2mention(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "UserProfile -&gt; Mention";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2Mentiong(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/userprofile/mention";
    }

    @RequestMapping(path="/userprofile/tickersymbol")
    public String domainCountUserprofile2Tickersymbol(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "UserProfile -&gt; TickerSymbol";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2TickerSymbol(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/userprofile/tickersymbol";
    }

    @RequestMapping(path="/userprofile/url")
    public String domainCountUserprofile2Url(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model) {
        String title = "UserProfile -&gt; Url";
        setUpThisPage(title,model);
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        Page<Object2Entity> listObject2Entity = userService.findAllUser2Url(pageRequest);
        model.addAttribute("listObject2Entity", listObject2Entity);
        return PATH+"/userprofile/url";
    }

    private void setUpThisPage(String title,Model model){
        String subtitle = "Counted Entities";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
    }


    @Autowired
    public CountedEntitiesController(TwitterwallFrontendProperties twitterwallFrontendProperties, ControllerHelper controllerHelper, TweetService tweetService, UserService userService) {
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.controllerHelper = controllerHelper;
        this.tweetService = tweetService;
        this.userService = userService;
    }



    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final ControllerHelper controllerHelper;

    private final TweetService tweetService;

    private final UserService userService;
}
