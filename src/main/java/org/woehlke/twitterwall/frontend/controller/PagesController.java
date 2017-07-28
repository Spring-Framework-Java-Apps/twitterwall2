package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.conf.TwitterwallFrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.mq.endoint.StartTask;


/**
 * Created by tw on 12.07.17.
 */
@Controller
public class PagesController {


    @RequestMapping("/")
    public ModelAndView index(Model model) {
        return new ModelAndView("redirect:/tweet/all");
    }

    @RequestMapping("/login")
    public String login(Model model) {
        log.info("-----------------------------------------");
        String symbol = Symbols.LEAF.toString();
        String title = "Login";
        String subtitle = "Enter your Credentials";
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        log.info("-----------------------------------------");
        return "login";
    }

    @RequestMapping("/imprint")
    public String imprint(Model model) {
        log.info("-----------------------------------------");
        String symbol = Symbols.IMPRINT.toString();
        String title = "Impressum";
        String subtitle = twitterwallFrontendProperties.getImprintSubtitle();
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        User user = startTask.createImprintUser();
        model.addAttribute("user", user);
        log.info("-----------------------------------------");
        return "imprint";
    }

    private static final Logger log = LoggerFactory.getLogger(PagesController.class);

    private final TwitterwallFrontendProperties twitterwallFrontendProperties;

    private final StartTask startTask;

    @Autowired
    public PagesController(TwitterwallFrontendProperties twitterwallFrontendProperties, StartTask startTask, ControllerHelper controllerHelper) {
        this.twitterwallFrontendProperties = twitterwallFrontendProperties;
        this.startTask = startTask;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;

}
