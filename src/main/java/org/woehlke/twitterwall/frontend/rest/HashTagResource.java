package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.HashTagService;


/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/hashtag")
public class HashTagResource {

  @RequestMapping(path="/count",method= RequestMethod.GET)
  public @ResponseBody long getCount() {
    return this.hashTagService.count();
  }

  @RequestMapping(path="/all",params = { "page" }, method= RequestMethod.GET)
  public @ResponseBody
  Page<HashTag> getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page) {
      Pageable pageRequest = new PageRequest(page, pageSize);
      return this.hashTagService.getAll(pageRequest);
  }

    @RequestMapping(path="/overview", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody Page<HashTag> getOverview(@RequestParam(name= "page" ,defaultValue=""+ControllerHelper.FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        return this.hashTagService.getAll(pageRequest);
    }

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    private final HashTagService hashTagService;

    @Autowired
    public HashTagResource(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

}
