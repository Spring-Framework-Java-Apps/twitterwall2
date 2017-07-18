package org.woehlke.twitterwall.frontend.controller.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
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
        log.info("twitterwall.frontend.menu.appname = "+menuAppName);
        log.info("twitter.searchQuery = "+searchterm);
        log.info("twitterwall.frontend.info.webpage = "+infoWebpage);
        log.info("twitterwall.frontend.theme = "+theme);
        log.info("twitterwall.context.test = "+contextTest);
        log.info("twitterwall.frontend.imprint.screenName = "+imprintScreenName);
        log.info("twitterwall.frontend.idGoogleAnalytics = "+idGoogleAnalytics);
        log.info("--------------------------------------------------------------------");
    }

    private Page setupPage(Page page, String title, String subtitle, String symbol)  {
        page.setTitle(title);
        page.setSubtitle(subtitle);
        page.setSymbol(symbol);
        page.setMenuAppName(menuAppName);
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        page.setContextTest(contextTest);
        page.setHistoryBack(true);
        if(!idGoogleAnalytics.isEmpty()){
            String html = GOOGLE_ANALYTICS_SCRIPT_HTML;
            html = html.replace("###GOOGLE_ANALYTICS_ID###",idGoogleAnalytics);
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

    private static final Logger log = LoggerFactory.getLogger(ControllerHelperImpl.class);


    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;

    @Value("${twitterwall.context.test}")
    private boolean contextTest;

    @Value("${twitterwall.frontend.imprint.screenName}")
    private String imprintScreenName;

    @Value("${twitterwall.frontend.imprint.subtitle}")
    private String imprintSubtitle;

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;

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
