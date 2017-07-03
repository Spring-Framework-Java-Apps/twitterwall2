package org.woehlke.twitterwall.frontend.rest.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.entities.TickerSymbol;
import org.woehlke.twitterwall.oodm.service.entities.TickerSymbolService;

import java.util.List;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/tickersymbol")
public class TickerSymbolResource {

  private final TickerSymbolService tickerSymbolService;

  @Autowired
  public TickerSymbolResource(TickerSymbolService tickerSymbolService) {
    this.tickerSymbolService = tickerSymbolService;
  }

  @RequestMapping(path="/count",method= RequestMethod.GET)
  public @ResponseBody
  long getCount() {
    return this.tickerSymbolService.count();
  }

  @RequestMapping(path="/all",method= RequestMethod.GET)
  public @ResponseBody
  List<TickerSymbol> getAll() {
    return this.tickerSymbolService.getAll();
  }
}
