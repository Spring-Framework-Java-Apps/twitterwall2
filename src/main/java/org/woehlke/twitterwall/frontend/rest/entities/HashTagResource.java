package org.woehlke.twitterwall.frontend.rest.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.entities.HashTag;
import org.woehlke.twitterwall.oodm.service.entities.HashTagService;

import java.util.List;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/hashtag")
public class HashTagResource {

  private final HashTagService hashTagService;

  @Autowired
  public HashTagResource(HashTagService hashTagService) {
    this.hashTagService = hashTagService;
  }

  @RequestMapping(path="/count",method= RequestMethod.GET)
  public @ResponseBody long getCount() {
    return this.hashTagService.count();
  }

  @RequestMapping(path="/all",method= RequestMethod.GET)
  public @ResponseBody List<HashTag> getAll() {
    return this.hashTagService.getAll();
  }

    @RequestMapping(path="/overview",method= RequestMethod.GET)
    public @ResponseBody List<HashTag> getOverview() {
        return this.hashTagService.getAll();
    }
}
