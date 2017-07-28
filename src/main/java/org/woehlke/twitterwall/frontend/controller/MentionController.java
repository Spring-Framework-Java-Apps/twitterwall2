package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.MentionService;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/mention")
public class MentionController {

    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model){
        String subtitle = "all";
        String title = "Mention";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize(), Sort.Direction.ASC,"screenName");
        Page<Mention> myPageContent = mentionService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "mention/all";
    }

    private final FrontendProperties frontendProperties;

    private final MentionService mentionService;

    private final ControllerHelper controllerHelper;

    @Autowired
    public MentionController(FrontendProperties frontendProperties, MentionService mentionService, ControllerHelper controllerHelper) {
        this.frontendProperties = frontendProperties;
        this.mentionService = mentionService;
        this.controllerHelper = controllerHelper;
    }

}
