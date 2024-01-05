package org.example.Controller;

import org.example.Entity.User;
import org.example.Models.RegisterUserRequest;
import org.example.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/ping")
    String ping() {
        return "Success123";
    }

    @PostMapping("/user")
    User register(@RequestBody RegisterUserRequest req){
        User user = userService.register(req.getName(), req.getEmail());
        return user;
    }
}
