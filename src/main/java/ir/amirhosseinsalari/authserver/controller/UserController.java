package ir.amirhosseinsalari.authserver.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class UserController {
    @RequestMapping(value = "/oauth/user-info",method = RequestMethod.GET)
    public User userInfo() {
        // TODO : load the user info from data base
        return new User("john","123", Collections.emptyList());
    }
}
