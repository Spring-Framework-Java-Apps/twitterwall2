package org.woehlke.twitterwall.frontend.rest.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.entities.Url;
import org.woehlke.twitterwall.oodm.service.entities.UrlService;

import java.util.List;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/url")
public class UrlResource {

  private final UrlService urlService;

  @Autowired
  public UrlResource(UrlService urlService) {
    this.urlService = urlService;
  }

  @RequestMapping(path="/count",method= RequestMethod.GET)
  public @ResponseBody
  long getCount() {
    return this.urlService.count();
  }

  @RequestMapping(path="/all",method= RequestMethod.GET)
  public @ResponseBody
  List<Url> getAll() {
    return this.urlService.getAll();
  }

}
