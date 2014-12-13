package controller;

import java.util.concurrent.atomic.AtomicLong;

import model.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/user/{userID}")
    public User user(@PathVariable int userID) {
        return User.getUserInfo(userID);
    }
}
