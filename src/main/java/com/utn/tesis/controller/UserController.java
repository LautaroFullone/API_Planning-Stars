package com.utn.tesis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tesis.model.User;
import com.utn.tesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user){
        User u = userService.addUser(user);
        return ResponseEntity.ok().body(u);
    }

    /*@GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userService.getUsers();

        if(userList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(userList.size()))
                    .body(userList);

    }*/

    @GetMapping
    public String getHello() {
        return "hello";

    }
}
