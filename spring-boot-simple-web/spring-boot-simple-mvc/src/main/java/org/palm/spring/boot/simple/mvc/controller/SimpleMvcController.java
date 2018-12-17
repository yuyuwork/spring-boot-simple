package org.palm.spring.boot.simple.mvc.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/simple")
public class SimpleMvcController {

    @RequestMapping(value = "/setUsername/{username}",method = RequestMethod.GET)
    public String setUsername(@PathVariable("username") String username,HttpServletRequest request) {
        request.getSession().setAttribute("username", "admin");

        return request.getSession().getId()+"："+String.format("user %s", username);
    }

}
