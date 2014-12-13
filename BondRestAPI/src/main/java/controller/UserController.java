package controller;

import java.util.concurrent.atomic.AtomicLong;

import model.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/user/{userID}")
    @ResponseBody
    public HttpEntity<User> user(@PathVariable int userID) {
        User user = User.getUserInfo(userID);
        user.add(linkTo(methodOn(UserController.class).user(userID)).withSelfRel());
        
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}