package com.utn.tesis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.model.*;
import com.utn.tesis.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.tesis.util.UTIL_CONSTANT.JWT_SECRET;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService,ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }
//-------------------------------------------- P O S T -----------------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) throws EmailExistException {
        User usr = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(usr);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User userLogging) throws JsonProcessingException {
        User finalUser = userService.login(userLogging);
        String userToken = this.generateToken(finalUser);

        return ResponseEntity.ok(LoginResponseDto.builder()
                .userDetails(finalUser)
                .token(userToken)
                .build());
    }
//-------------------------------------------- G E T -------------------------------------------------------------------
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

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId){
        User searchUser = userService.getUserById(userId);
        return ResponseEntity.ok(searchUser);
    }
// -------------------------------------------- P U T ------------------------------------------------------------------
    @PutMapping("/{userId}")
    public ResponseEntity<User> modifyUser(@PathVariable Integer userId, @RequestBody User newUser){
        User usr = userService.modifyUser(userId, newUser);
        return ResponseEntity.ok(usr);
    }
//------------------------------------------------- T O K E N ----------------------------------------------------------
    public String generateToken(User user) throws JsonProcessingException {
        String role = "USER";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

        String token = Jwts.builder()
                .setId("JWT")
                .setSubject(user.getEmail())
                .claim("user",objectMapper.writeValueAsString(user))
                .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 180000000 ))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();

        return token;
    }
}
