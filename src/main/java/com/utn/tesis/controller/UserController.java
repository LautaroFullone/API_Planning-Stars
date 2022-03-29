package com.utn.tesis.controller;

import com.utn.tesis.exception.types.EmailExistException;
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

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody User user) throws EmailExistException {
        User usr = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(usr);
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User userLogging){
        User finalUser = userService.login(userLogging);
        return ResponseEntity.status(HttpStatus.OK).body(finalUser);
    }
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userService.getUsers();

        if(userList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(userList.size()))
                    .body(userList);

    }

}
