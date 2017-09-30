package org.woehlke.twitterwall.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woehlke.twitterwall.frontend.content.ContentFactory;
import org.woehlke.twitterwall.frontend.content.Symbols;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model) {
        log.debug("-----------------------------------------");
        String symbol = Symbols.LOGIN.toString();
        String title = "Login";
        String subtitle = "Enter your Credentials";
        model = contentFactory.setupPage(model, title, subtitle, symbol);
        log.debug("-----------------------------------------");
        return "login/login";
    }

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final ContentFactory contentFactory;

    @Autowired
    public LoginController(
        ContentFactory contentFactory
    ) {
        this.contentFactory = contentFactory;
    }
}
