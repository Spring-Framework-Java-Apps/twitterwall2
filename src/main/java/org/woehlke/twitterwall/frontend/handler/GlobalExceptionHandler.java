package org.woehlke.twitterwall.frontend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.frontend.common.Symbols;
import org.woehlke.twitterwall.frontend.model.Page;
import org.woehlke.twitterwall.oodm.exceptions.controller.ControllerRequestParameterSyntaxException;
import org.woehlke.twitterwall.oodm.exceptions.controller.ImprintException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tw on 17.06.17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${twitterwall.frontend.menu.appname}")
    private String menuAppName;

    @Value("${twitterwall.frontend.menu.users}")
    private boolean showMenuUsers;

    @Value("${twitter.searchQuery}")
    private String searchterm;

    @Value("${twitterwall.frontend.info.webpage}")
    private String infoWebpage;

    @Value("${twitterwall.frontend.theme}")
    private String theme;
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(HttpServletRequest request, Exception ex) {
        log.warn("IllegalArgumentException occured :: URL=" + request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }
    
    private ModelAndView getTemplate(HttpServletRequest request, Exception ex) {
        Page page = new Page();
        page.setSymbol(Symbols.EXCEPTION.toString());
        page.setMenuAppName(menuAppName);
        page.setTitle("Exception");
        page.setSubtitle(ex.getMessage());
        page.setShowMenuUsers(showMenuUsers);
        page.setTwitterSearchTerm(searchterm);
        page.setInfoWebpage(infoWebpage);
        page.setTheme(theme);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("page", page);
        mav.setViewName("persistentObjectNotFound");
        return mav;
    }

    @ExceptionHandler(ControllerRequestParameterSyntaxException.class)
    public ModelAndView handleControllerRequestParameterSyntaxException(HttpServletRequest request, Exception ex) {
        log.warn("ControllerRequestParameterSyntaxException occured :: URL=" + request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }
    
    @ExceptionHandler(ImprintException.class)
    public ModelAndView handleImprintException(HttpServletRequest request, Exception ex) {
        log.warn("ImprintException occured :: URL=" + request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }
}
