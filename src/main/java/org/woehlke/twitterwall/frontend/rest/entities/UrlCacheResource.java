package org.woehlke.twitterwall.frontend.rest.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.entities.UrlCache;
import org.woehlke.twitterwall.oodm.service.entities.UrlCacheService;

import java.util.List;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/urlcache")
public class UrlCacheResource {

  private final UrlCacheService urlCacheService;;

  @Autowired
  public UrlCacheResource(UrlCacheService urlCacheService) {
    this.urlCacheService = urlCacheService;
  }

  @RequestMapping(path="/count",method= RequestMethod.GET)
  public @ResponseBody
  long getCount() {
    return this.urlCacheService.count();
  }

  @RequestMapping(path="/all",method= RequestMethod.GET)
  public @ResponseBody
  List<UrlCache> getAll() {
    return this.urlCacheService.getAll();
  }
}
