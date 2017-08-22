package org.woehlke.twitterwall.frontend.content;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tw on 18.07.17.
 */
public interface ContentFactory {

    int FIRST_PAGE_NUMBER = 0;

    ModelAndView setupPage(ModelAndView mav, String title, String subtitle, String symbol);

    Model setupPage(Model model, String title, String subtitle, String symbol);
}
