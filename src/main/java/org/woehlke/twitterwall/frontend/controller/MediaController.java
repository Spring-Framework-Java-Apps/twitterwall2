package org.woehlke.twitterwall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.Media;
import org.woehlke.twitterwall.oodm.service.MediaService;

/**
 * Created by tw on 16.07.17.
 */
@Controller
@RequestMapping("/media")
public class MediaController {


    @RequestMapping(path="/all")
    public String getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page, Model model){
        controllerHelper.logEnv();
        String subtitle = "all";
        String title = "Media";
        String symbol = Symbols.DATABASE.toString();
        model = controllerHelper.setupPage(model,title,subtitle,symbol);
        Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC,"url");
        Page<Media> myPageContent = mediaService.getAll(pageRequest);
        model.addAttribute("myPageContent",myPageContent);
        return "/media/all";
    }


    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService, ControllerHelper controllerHelper) {
        this.mediaService = mediaService;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;

}
