package org.woehlke.twitterwall.frontend.controller.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.TwitterProperties;
import org.woehlke.twitterwall.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.frontend.model.Page;

/**
 * Created by tw on 18.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class ControllerHelperImpl implements ControllerHelper {

    public void logEnv(){
        log.info("--------------------------------------------------------------------");
        log.info("twitter.searchQuery = "+  twitterProperties.getSearchQuery());
        log.info("twitterwall.frontend.menuAppName = "+ twitterwallFrontendProperties.getMenuAppName());
        log.info("twitterwall.frontend.infoWebpage = "+ twitterwallFrontendProperties.getInfoWebpage());
        log.info("twitterwall.frontend.theme = "+ twitterwallFrontendProperties.getTheme());
        log.info("twitterwall.frontend.contextTest = "+ twitterwallFrontendProperties.getContextTest());
        log.info("twitterwall.frontend.imprintScreenName = "+ twitterwallFrontendProperties.getImprintScreenName());
        log.info("twitterwall.frontend.idGoogleAnalytics = "+ twitterwallFrontendProperties.getIdGoogleAnalytics());
        log.info("--------------------------------------------------------------------");
    }

    private Page setupPage(Page page, String title, String subtitle, String symbol)  {
        page.setTitle(title);
        page.setSubtitle(subtitle);
        page.setSymbol(symbol);
        page.setMenuAppName(twitterwallFrontendProperties.getMenuAppName());
        page.setTwitterSearchTerm(twitterProperties.getSearchQuery());
        page.setInfoWebpage(twitterwallFrontendProperties.getInfoWebpage());
        page.setTheme(twitterwallFrontendProperties.getTheme());
        page.setContextTest(twitterwallFrontendProperties.getContextTest());
        page.setHistoryBack(true);
        if(!twitterwallFrontendProperties.getIdGoogleAnalytics().isEmpty()){
            String html = GOOGLE_ANALYTICS_SCRIPT_HTML;
            html = html.replace("###GOOGLE_ANALYTICS_ID###", twitterwallFrontendProperties.getIdGoogleAnalytics());
            page.setGoogleAnalyticScriptHtml(html);
        } else {
            page.setGoogleAnalyticScriptHtml("");
        }
        logEnv();
        log.info("--------------------------------------------------------------------");
        log.info("setupPage = "+page.toString());
        log.info("--------------------------------------------------------------------");
        return page;
    }

    public ModelAndView setupPage(ModelAndView mav, String title, String subtitle, String symbol) {
        Page page = new Page();
        page = setupPage(page, title, subtitle, symbol);
        log.info("page: "+page.toString());
        mav.addObject("page", page);
        return mav;
    }

    public Model setupPage(Model model, String title, String subtitle, String symbol) {
        Page page = new Page();
        page = setupPage(page, title, subtitle, symbol);
        log.info("page: "+page.toString());
        model.addAttribute("page", page);
        return model;
    }

    @Autowired
    public ControllerHelperImpl(TwitterwallFrontendProperties twitterwallFrontendProperties, TwitterProperties twitterProperties) {
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.twitterProperties = twitterProperties;
    }

    private static final Logger log = LoggerFactory.getLogger(ControllerHelperImpl.class);

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final TwitterProperties twitterProperties;

    private final static String GOOGLE_ANALYTICS_SCRIPT_HTML = "<script>\n" +
            "        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" +
            "                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" +
            "            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" +
            "        })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');\n" +
            "\n" +
            "        ga('create', '###GOOGLE_ANALYTICS_ID###', 'auto');\n" +
            "        ga('send', 'pageview');\n" +
            "    </script>";
}
