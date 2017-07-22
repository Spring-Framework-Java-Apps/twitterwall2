package org.woehlke.twitterwall.frontend.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tw on 17.06.17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
        mav = controllerHelper.setupPage(mav, title, subtitle, symbol);
        mav.addObject("exception", ex);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("exceptionhandler/persistentObjectNotFound");
        return mav;
    }

    @Autowired
    public GlobalExceptionHandler(ControllerHelper controllerHelper) {
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;
}
