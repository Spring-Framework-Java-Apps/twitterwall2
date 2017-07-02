package org.woehlke.twitterwall.frontend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.backend.TwitterApiService;
import org.woehlke.twitterwall.frontend.common.AbstractTwitterwallController;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tw on 17.06.17.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends AbstractTwitterwallController {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @Value("${twitterwall.frontend.idGoogleAnalytics}")
    private String idGoogleAnalytics;


    private final TwitterApiService twitterApiService;

    private final PersistDataFromTwitter persistDataFromTwitter;

    @Autowired
    public GlobalExceptionHandler(TwitterApiService twitterApiService, PersistDataFromTwitter persistDataFromTwitter) {
        this.twitterApiService = twitterApiService;
        this.persistDataFromTwitter = persistDataFromTwitter;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(HttpServletRequest request, Exception ex) {
        log.warn("IllegalArgumentException occured :: URL=" + request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }
    
    private ModelAndView getTemplate(HttpServletRequest request, Exception ex) {
        ModelAndView mav = new ModelAndView();
        String symbol = Symbols.EXCEPTION.toString();
        String title = "Exception";
        String subtitle = ex.getMessage();
        mav = super.setupPage(mav, title, subtitle, symbol);
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("persistentObjectNotFound");
        return mav;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setupAfterPropertiesSetWithTesting(twitterApiService,persistDataFromTwitter,menuAppName,searchterm,infoWebpage,theme,contextTest,imprintScreenName,idGoogleAnalytics);
    }
}
