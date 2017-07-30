package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.woehlke.twitterwall.conf.properties.FrontendProperties;
import org.woehlke.twitterwall.frontend.controller.common.ControllerHelper;
import org.woehlke.twitterwall.oodm.entities.User;
import org.woehlke.twitterwall.oodm.service.UserService;

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

    @RequestMapping(path="/all", params = { "page" }, method= RequestMethod.GET)
    public @ResponseBody
    Page<User> getAll(
        @RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page
    ) {
        Pageable pageRequest = new PageRequest(page, frontendProperties.getPageSize());
        return this.userService.getAll(pageRequest);
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public @ResponseBody
    User findById(
        @PathVariable("id") User user
    ) {
        return user;
    }

    private final UserService userService;

    private final FrontendProperties frontendProperties;

    @Autowired
    public UserResource(
            UserService userService,
            FrontendProperties frontendProperties
    ) {
        this.userService = userService;
        this.frontendProperties = frontendProperties;
    }

}
