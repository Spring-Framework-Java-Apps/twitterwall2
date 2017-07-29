package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.entities.Mention;
import org.woehlke.twitterwall.oodm.service.MediaService;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/media")
public class MediaResource {

    @RequestMapping(path="/count", method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
    return this.mediaService.count();
    }

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody Page<Media> getAll(
        @RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page
    ) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        return this.mediaService.getAll(pageRequest);
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public @ResponseBody Media findById(
            @PathVariable("id") Media media
    ) {
        return media;
    }

    private final MediaService mediaService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public MediaResource(
            MediaService mediaService,
            FrontendProperties frontendProperties
    ) {
        this.mediaService = mediaService;
        this.frontendProperties = frontendProperties;
    }

}
