package org.example.tatrainingapi.api;

import org.example.tatrainingapi.model.User;
import org.example.tatrainingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable(required = true) long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @PutMapping(consumes={MediaType.APPLICATION_JSON_VALUE})
    public void getUsersByName(@Valid @RequestBody User user) {
        userService.addUser(user);
    }
}
