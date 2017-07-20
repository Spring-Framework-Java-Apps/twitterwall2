package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.woehlke.twitterwall.ConfigTwitterwall;
import org.woehlke.twitterwall.frontend.controller.common.Symbols;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.scheduled.service.facade.CreateTestData;


/**
 * Created by tw on 12.07.17.
 */
@Controller
public class PagesController {


    @RequestMapping("/")
    public ModelAndView index(Model model) {
        return new ModelAndView("redirect:/tweet/all");
    }

    @RequestMapping("/imprint")
    public String imprint(Model model) {
        log.info("-----------------------------------------");
        controllerHelper.logEnv();
        String symbol = Symbols.IMPRINT.toString();
        String title = "Impressum";
        String subtitle = configTwitterwall.getFrontend().getImprintSubtitle();
        model = controllerHelper.setupPage(model, title, subtitle, symbol);
        String screenName = configTwitterwall.getFrontend().getImprintScreenName();
        User user = createTestData.addUserForScreenName(screenName);
        model.addAttribute("user", user);
        log.info("-----------------------------------------");
        return "imprint";
    }

    private static final Logger log = LoggerFactory.getLogger(PagesController.class);

    //@Value("${twitterwall.frontend.imprintScreenName}")
    //private String imprintScreenName;

    //@Value("${twitterwall.frontend.imprintSubtitle}")
    //private String imprintSubtitle;

    private final CreateTestData createTestData;

    private final ConfigTwitterwall configTwitterwall;

    @Autowired
    public PagesController(CreateTestData createTestData, ConfigTwitterwall configTwitterwall, ControllerHelper controllerHelper) {
        this.createTestData = createTestData;
        this.configTwitterwall = configTwitterwall;
        this.controllerHelper = controllerHelper;
    }

    private final ControllerHelper controllerHelper;

}
