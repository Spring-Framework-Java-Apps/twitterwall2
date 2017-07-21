package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Mention;
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
    public @ResponseBody
    Page<Mention> getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, twitterwallFrontendProperties.getPageSize());
        return this.mentionService.getAll(pageRequest);
    }

    private final MentionService mentionService;

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    @Autowired
    public MentionResource(MentionService mentionService, TwitterwallFrontendProperties twitterwallFrontendProperties) {
        this.mentionService = mentionService;
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
    }

}
