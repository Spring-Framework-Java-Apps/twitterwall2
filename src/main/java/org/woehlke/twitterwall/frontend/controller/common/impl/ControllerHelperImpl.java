package org.woehlke.twitterwall.frontend.controller.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.conf.properties.TwitterProperties;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.transients.PageContent;

/**
 * Created by tw on 18.07.17.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public class ControllerHelperImpl implements ControllerHelper {

    private PageContent setupPage(PageContent pageContent, String title, String subtitle, String symbol)  {
        pageContent.setTitle(title);
        pageContent.setSubtitle(subtitle);
        pageContent.setSymbol(symbol);
        pageContent.setMenuAppName(frontendProperties.getMenuAppName());
        pageContent.setTwitterSearchTerm(twitterProperties.getSearchQuery());
        pageContent.setInfoWebpage(frontendProperties.getInfoWebpage());
        pageContent.setTheme(frontendProperties.getTheme());
        pageContent.setContextTest(frontendProperties.getContextTest());
        pageContent.setHistoryBack(true);
        if(!frontendProperties.getIdGoogleAnalytics().isEmpty()){
            String html = GOOGLE_ANALYTICS_SCRIPT_HTML;
            html = html.replace("###GOOGLE_ANALYTICS_ID###", frontendProperties.getIdGoogleAnalytics());
            pageContent.setGoogleAnalyticScriptHtml(html);
        } else {
            pageContent.setGoogleAnalyticScriptHtml("");
        }
        log.info("--------------------------------------------------------------------");
        log.info("setupPage = "+ pageContent.toString());
        log.info("--------------------------------------------------------------------");
        return pageContent;
    }

    public ModelAndView setupPage(ModelAndView mav, String title, String subtitle, String symbol) {
        PageContent pageContent = new PageContent();
        pageContent = setupPage(pageContent, title, subtitle, symbol);
        log.info("pageContent: "+ pageContent.toString());
        mav.addObject("pageContent", pageContent);
        return mav;
    }

    public Model setupPage(Model model, String title, String subtitle, String symbol) {
        PageContent pageContent = new PageContent();
        pageContent = setupPage(pageContent, title, subtitle, symbol);
        log.info("pageContent: "+ pageContent.toString());
        model.addAttribute("pageContent", pageContent);
        return model;
    }

    private static final Logger log = LoggerFactory.getLogger(ControllerHelperImpl.class);

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
    public ControllerHelperImpl(
            FrontendProperties frontendProperties,
            TwitterProperties twitterProperties
    ) {
        this.frontendProperties = frontendProperties;
        this.twitterProperties = twitterProperties;
    }
}
