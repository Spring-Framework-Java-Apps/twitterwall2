package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.entities.TaskHistory;
import org.woehlke.twitterwall.oodm.service.MentionService;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/mention")
public class MentionResource {

    @RequestMapping(path="/count", method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
    return this.mentionService.count();
    }

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody Page<Mention> getAll(
        @RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page
    ) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        return this.mentionService.getAll(pageRequest);
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public @ResponseBody Mention findById(
        @PathVariable("id") Mention mention
    ) {
        return mention;
    }

    private final MentionService mentionService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public MentionResource(
            MentionService mentionService,
            FrontendProperties frontendProperties
    ) {
        this.mentionService = mentionService;
        this.frontendProperties = frontendProperties;
    }

}
