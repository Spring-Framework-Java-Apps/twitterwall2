package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.endoint.StartTask;


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
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        User user = startTask.createImprintUser();
        model.addAttribute("user", user);
        log.info("-----------------------------------------");
        return "imprint/imprint";
    }

    private static final Logger log = LoggerFactory.getLogger(ImprintController.class);

    private final FrontendProperties frontendProperties;

    private final StartTask startTask;

    private final ControllerHelper controllerHelper;

    @Autowired
    public ImprintController(
            FrontendProperties frontendProperties,
            StartTask startTask,
            ControllerHelper controllerHelper
    ) {
        this.frontendProperties = frontendProperties;
        this.startTask = startTask;
        this.controllerHelper = controllerHelper;
    }

}
