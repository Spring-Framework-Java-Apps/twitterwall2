package org.woehlke.twitterwall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.model.Page;
import org.woehlke.twitterwall.oodm.exceptions.*;

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

    @ExceptionHandler(FindEntitiesByIdTwitterFromTweetException.class)
    public ModelAndView handleFindByIdTwitterFromTweetException(HttpServletRequest request, Exception ex){
        log.warn("FindEntitiesByIdTwitterFromTweetException occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindHashTagByTextException.class)
    public ModelAndView handleFindHashTagByTextException(HttpServletRequest request, Exception ex){
        log.warn("FindHashTagByTextException occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindMediaByFieldsExceptionException.class)
    public ModelAndView handleFindMediaByFieldsException(HttpServletRequest request, Exception ex){
        log.warn("FindMediaByFieldsException occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindMediaByIdTwitterException.class)
    public ModelAndView handleFindMediaByIdTwitter(HttpServletRequest request, Exception ex){
        log.warn("FindMediaByIdTwitter occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindMentionByIdTwitterException.class)
    public ModelAndView handleFindMentionByIdTwitter(HttpServletRequest request, Exception ex){
        log.warn("FindMediaByIdTwitter occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindMentionByScreenNameAndNameException.class)
    public ModelAndView handleFindMentionByScreenNameAndName(HttpServletRequest request, Exception ex){
        log.warn("FindMentionByScreenNameAndName occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindTickerSymbolByTickerSymbolAndUrlException.class)
    public ModelAndView handleFindTickerSymbolByTickerSymbolAndUrl(HttpServletRequest request, Exception ex){
        log.warn("FindTickerSymbolByTickerSymbolAndUrl occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindTweetByIdTwitterException.class)
    public ModelAndView handleFindTweetByIdTwitterl(HttpServletRequest request, Exception ex){
        log.warn("FindTweetByIdTwitter occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindUrlByDisplayExpandedUrlException.class)
    public ModelAndView handleFindUrlByDisplayExpandedUrl(HttpServletRequest request, Exception ex){
        log.warn("FindTweetByIdTwitter occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindUserByIdTwitterException.class)
    public ModelAndView handleFindUserByIdTwitter(HttpServletRequest request, Exception ex){
        log.warn("FindUserByIdTwitter occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindUserByScreenNameException.class)
    public ModelAndView handleFindUserByScreenName(HttpServletRequest request, Exception ex){
        log.warn("FindUserByScreenName occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    private ModelAndView getTemplate(HttpServletRequest request, Exception ex){
        Page page = new Page();
        page.setMenuAppName(menuAppName);
        page.setTitle("Exception");
        page.setSubtitle(ex.getMessage());
        page.setShowMenuUsers(showMenuUsers);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("page",page);
        mav.setViewName("persistentObjectNotFound");
        return mav;
    }

    @ExceptionHandler(ControllerRequestParameterSyntaxException.class)
    public ModelAndView handleControllerRequestParameterSyntaxException(HttpServletRequest request, Exception ex){
        log.warn("ControllerRequestParameterSyntaxException occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

    @ExceptionHandler(FindUrlByUrlException.class)
    public ModelAndView handleFindUrlByUrlException(HttpServletRequest request, Exception ex){
        log.warn("FindUrlByUrlException occured :: URL="+request.getRequestURL());
        log.warn(ex.getMessage());
        return getTemplate(request, ex);
    }

}