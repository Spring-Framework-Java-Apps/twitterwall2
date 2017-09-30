package org.woehlke.twitterwall.frontend.controller.pages;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.configuration.properties.*;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.frontend.content.Symbols;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${server.error.path:/error}")
public class ErrorController extends AbstractErrorController {


    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response, Exception exception) {
        HttpStatus status = getStatus(request);
        boolean isIncludeStackTrace = true;
        Map<String, Object> model = getErrorAttributes(
            request, isIncludeStackTrace);
        response.setStatus(status.value());

        String msg = "url: "+ request.getRequestURL().toString();
        log.error(msg);

        String symbol = Symbols.EXCEPTION.toString();
        String title = "Fehler";
        String subtitle = "Ein Fehler ist aufgetreten";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        model.put("ex",exception);
        ModelAndView modelAndView = resolveErrorView(request, response, status, Collections.unmodifiableMap(model));
        modelAndView = (modelAndView == null ? new ModelAndView("exceptionhandler/error", Collections.unmodifiableMap(model)) : modelAndView);
        modelAndView.setViewName("exceptionhandler/error");
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return otherProperties.getServer().getError().getPath();
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        boolean isIncludeStackTrace = false;
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace);
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes, ContentFactory contentFactory, BackendProperties backendProperties, FrontendProperties frontendProperties, SchedulerProperties schedulerProperties, TestdataProperties testdataProperties, TwitterProperties twitterProperties, OtherProperties otherProperties) {
        this(errorAttributes,
            Collections.<ErrorViewResolver>emptyList(),contentFactory, backendProperties, frontendProperties, schedulerProperties,testdataProperties, twitterProperties, otherProperties);
    }


    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);


    private ErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers, ContentFactory contentFactory, BackendProperties backendProperties, FrontendProperties frontendProperties, SchedulerProperties schedulerProperties, TestdataProperties testdataProperties, TwitterProperties twitterProperties, OtherProperties otherProperties) {
        super(errorAttributes, errorViewResolvers);
        this.contentFactory = contentFactory;
        this.backendProperties = backendProperties;
        this.frontendProperties = frontendProperties;
        this.schedulerProperties = schedulerProperties;
        this.testdataProperties = testdataProperties;
        this.twitterProperties = twitterProperties;
        this.otherProperties = otherProperties;
    }

    private final ContentFactory contentFactory;

    private final BackendProperties backendProperties;

    private final FrontendProperties frontendProperties;

    private final SchedulerProperties schedulerProperties;

    private final TestdataProperties testdataProperties;

    private final TwitterProperties twitterProperties;

    private final OtherProperties otherProperties;


}
