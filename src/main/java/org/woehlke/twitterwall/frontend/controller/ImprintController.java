package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.configuration.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.content.Symbols;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.oodm.model.User;
import org.woehlke.twitterwall.backend.mq.endpoint.tasks.TaskStart;


/**
 * Created by tw on 12.07.17.
 */
@Controller
public class ImprintController {

    @RequestMapping("/imprint")
    public String imprint(Model model) {
        log.info("-----------------------------------------");
        String symbol = Symbols.IMPRINT.toString();
        String title = "Imprint";
        String subtitle = frontendProperties.getImprintSubtitle();
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        User user = taskStart.createImprintUser();
        model.addAttribute("user", user);
        log.info("-----------------------------------------");
        return "imprint/imprint";
    }

    private static final Logger log = LoggerFactory.getLogger(ImprintController.class);

    private final FrontendProperties frontendProperties;

    private final TaskStart taskStart;

    private final ContentFactory contentFactory;

    @Autowired
    public ImprintController(
            FrontendProperties frontendProperties,
            TaskStart taskStart,
            ContentFactory contentFactory
    ) {
        this.frontendProperties = frontendProperties;
        this.taskStart = taskStart;
        this.contentFactory = contentFactory;
    }

}
