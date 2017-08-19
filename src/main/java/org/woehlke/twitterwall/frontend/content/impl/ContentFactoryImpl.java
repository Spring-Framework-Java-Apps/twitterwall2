package org.woehlke.twitterwall.frontend.content.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.frontend.content.Page;

/**
 * Created by tw on 18.07.17.
 */
@Component
public class ContentFactoryImpl implements ContentFactory {

    private Page setupPage(Page page, String title, String subtitle, String symbol)  {
        page.setTitle(title);
        page.setSubtitle(subtitle);
        page.setSymbol(symbol);
        page.setMenuAppName(frontendProperties.getMenuAppName());
        page.setTwitterSearchTerm(twitterProperties.getSearchQuery());
        page.setInfoWebpage(frontendProperties.getInfoWebpage());
        page.setTheme(frontendProperties.getTheme());
        page.setContextTest(frontendProperties.getContextTest());
        page.setHistoryBack(true);
        if(!frontendProperties.getIdGoogleAnalytics().isEmpty()){
            String html = GOOGLE_ANALYTICS_SCRIPT_HTML;
            html = html.replace("###GOOGLE_ANALYTICS_ID###", frontendProperties.getIdGoogleAnalytics());
            page.setGoogleAnalyticScriptHtml(html);
        } else {
            page.setGoogleAnalyticScriptHtml("");
        }
        log.debug("--------------------------------------------------------------------");
        log.debug("setupPage = "+page.toString());
        log.debug("--------------------------------------------------------------------");
        return page;
    }

    public ModelAndView setupPage(ModelAndView mav, String title, String subtitle, String symbol) {
        Page page = new Page();
        page = setupPage(page, title, subtitle, symbol);
        log.debug("page: "+page.toString());
        mav.addObject("page", page);
        return mav;
    }

    public Model setupPage(Model model, String title, String subtitle, String symbol) {
        Page page = new Page();
        page = setupPage(page, title, subtitle, symbol);
        log.debug("page: "+page.toString());
        model.addAttribute("page", page);
        return model;
    }

    private static final Logger log = LoggerFactory.getLogger(ContentFactoryImpl.class);

    private final FrontendProperties frontendProperties;

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


    @Autowired
    public ContentFactoryImpl(
            FrontendProperties frontendProperties,
            TwitterProperties twitterProperties
    ) {
        this.frontendProperties = frontendProperties;
        this.twitterProperties = twitterProperties;
    }
}
