package com.utn.tesis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.model.LoginResponseDto;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserModify;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService,ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper=objectMapper;
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
    @GetMapping("/{iduser}/parties")
    public ResponseEntity<List<Party>>getUserParties (@PathVariable Integer iduser) {
        List<Party> partyList = userService.getPartyList(iduser);

        if(partyList.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Integer.toString(partyList.size()))
                    .body(partyList);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId ){
        User searchUser = userService.getUserById(userId);
        return ResponseEntity.ok(searchUser);
    }
// -------------------------------------------- D E L E T --------------------------------------------------------------
// -------------------------------------------- P U T ------------------------------------------------------------------
    @PutMapping("/{idUser}")
    public ResponseEntity<User> modifyUser(@PathVariable Integer idUser,@RequestBody UserModify newUser){
        User usr=userService.modifyUser(idUser,newUser);
        return ResponseEntity.ok(usr);
    }
//------------------------------------------------- T O K E N ----------------------------------------------------------
    public String generateToken(User user) throws JsonProcessingException {
        String role ="USER";
        //parte de spring security que permite la utilizacion de roles jwt
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
