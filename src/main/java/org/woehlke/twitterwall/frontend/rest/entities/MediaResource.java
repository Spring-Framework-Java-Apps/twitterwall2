package org.woehlke.twitterwall.frontend.rest.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.entities.Media;
import org.woehlke.twitterwall.oodm.service.entities.MediaService;

import java.util.List;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/media")
public class MediaResource {

    private final MediaService mediaService;

    @Autowired
    public MediaResource(MediaService mediaService) {
    this.mediaService = mediaService;
    }

    @RequestMapping(path="/count",method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
    return this.mediaService.count();
    }

    @RequestMapping(path="/all",method= RequestMethod.GET)
    public @ResponseBody
    List<Media> getAll() {
    return this.mediaService.getAll();
    }
}
