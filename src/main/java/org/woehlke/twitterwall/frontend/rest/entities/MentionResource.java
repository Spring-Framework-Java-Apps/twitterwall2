package org.woehlke.twitterwall.frontend.rest.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.entities.Mention;
import org.woehlke.twitterwall.oodm.service.entities.MentionService;

import java.util.List;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/mention")
public class MentionResource {

    private final MentionService mentionService;

    @Autowired
    public MentionResource(MentionService mentionService) {
    this.mentionService = mentionService;
    }

    @RequestMapping(path="/count",method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
    return this.mentionService.count();
    }

    @RequestMapping(path="/all",method= RequestMethod.GET)
    public @ResponseBody
    List<Mention> getAll() {
    return this.mentionService.getAll();
    }

}
