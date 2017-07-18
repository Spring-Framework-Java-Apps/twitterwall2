package org.woehlke.twitterwall.frontend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    Page<User> getAll(@RequestParam(name= "page" ,defaultValue=""+ ControllerHelper.FIRST_PAGE_NUMBER) int page) {
        Pageable pageRequest = new PageRequest(page, pageSize);
        return this.userService.getAll(pageRequest);
    }

    @Value("${twitterwall.frontend.maxResults}")
    private int pageSize;

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
    this.userService = userService;
    }

}
