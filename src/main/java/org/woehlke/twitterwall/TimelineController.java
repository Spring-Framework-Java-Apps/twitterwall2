package org.woehlke.twitterwall;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tw on 10.06.17.
 */
@Controller
public class TimelineController {

    @RequestMapping("/")
    public String greeting(Model model) {
        return "timeline";
    }
}
