package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;

import java.util.List;

/**
 * Created by tw on 03.07.17.
 */
@Controller
@RequestMapping("/rest/user")
public class UserResource {

    @RequestMapping(path="/count",method= RequestMethod.GET)
    public @ResponseBody
    long getCount() {
        return this.userService.count();
    }

    @RequestMapping(path="/all",method= RequestMethod.GET)
    public @ResponseBody List<User> getAll() {
        return this.userService.getAll();
    }

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
    this.userService = userService;
    }

}
