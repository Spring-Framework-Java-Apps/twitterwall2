package org.woehlke.twitterwall.frontend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.frontend.model.CountedEntities;
import org.woehlke.twitterwall.scheduled.PersistDataFromTwitter;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/service")
public class ServiceResource {

     private final PersistDataFromTwitter persistDataFromTwitter;

     @Autowired
    public ServiceResource(PersistDataFromTwitter persistDataFromTwitter) {
        this.persistDataFromTwitter = persistDataFromTwitter;
    }
    @RequestMapping(path="/count",method= RequestMethod.GET)
    public @ResponseBody
    CountedEntities countAll(){
        return this.persistDataFromTwitter.countAll();
    }
}
