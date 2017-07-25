package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping(path="/application/countedEntities")
public class CountedEntitiesController {

    private final static String PATH="application/countedEntities";


    @RequestMapping(path="/tweet/hashtag")
    public String domainCountTweet2hashtag(Model model) {
        String title = "Tweet -&gt; HashTag";
        setUpThisPage(title,model);
        return PATH+"/tweet/hashtag";
    }

    @RequestMapping(path="/tweet/media")
    public String domainCountTweet2media(Model model) {
        String title = "Tweet -&gt; Media";
        setUpThisPage(title,model);
        return PATH+"/tweet/media";
    }

    @RequestMapping(path="/tweet/mention")
    public String domainCountTweet2mention(Model model) {
        String title = "Tweet -&gt; Mention";
        setUpThisPage(title,model);
        return PATH+"/tweet/mention";
    }

    @RequestMapping(path="/tweet/tickersymbol")
    public String domainCountTweet2tickersymbol(Model model) {
        String title = "Tweet -&gt; TickerSymbol";
        setUpThisPage(title,model);
        return PATH+"/tweet/tickersymbol";
    }

    @RequestMapping(path="/tweet/url")
    public String domainCountTweet2url(Model model) {
        String title = "Tweet -&gt; Url";
        setUpThisPage(title,model);
        return PATH+"/tweet/url";
    }

    @RequestMapping(path="/userprofile/hashtag")
    public String domainCountUserprofile2hashtag(Model model) {
        String title = "UserProfile -&gt; HashTag";
        setUpThisPage(title,model);
        return PATH+"/userprofile/hashtag";
    }

    @RequestMapping(path="/userprofil/media")
    public String domainCountUserprofile2media(Model model) {
        String title = "UserProfile -&gt; Media";
        setUpThisPage(title,model);
        return PATH+"/userprofile/media";
    }

    @RequestMapping(path="/userprofile/mention")
    public String domainCountUserprofile2mention(Model model) {
        String title = "UserProfile -&gt; Mention";
        setUpThisPage(title,model);
        return PATH+"/userprofile/mention";
    }

    @RequestMapping(path="/userprofile/tickersymbol")
    public String domainCountUserprofile2Tickersymbol(Model model) {
        String title = "UserProfile -&gt; TickerSymbol";
        setUpThisPage(title,model);
        return PATH+"/userprofile/tickersymbol";
    }

    @RequestMapping(path="/userprofile/url")
    public String domainCountUserprofile2Url(Model model) {
        String title = "UserProfile -&gt; Url";
        setUpThisPage(title,model);
        return PATH+"/userprofile/url";
    }

    private void setUpThisPage(String title,Model model){
        String subtitle = "Counted Entities";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
    }


    @Autowired
    public CountedEntitiesController(ControllerHelper controllerHelper) {
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;
}
