package com.utn.tesis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tesis.UTILS_TESTCONSTANTS;
import com.utn.tesis.model.User;
import com.utn.tesis.model.dto.LoginResponseDto;
import com.utn.tesis.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStoryController_Test {
    UserService userService;
    ObjectMapper objectMapper;
    UserController userController;

    @Before
    public void setUp(){
        userService=mock(UserService.class);
        objectMapper = mock(ObjectMapper.class);
        userController=new UserController(userService,objectMapper);
    }
    @Test
    public void register_TestOK(){
        when(userService.registerUser(UTILS_TESTCONSTANTS.getUser())).thenReturn(UTILS_TESTCONSTANTS.getUser());
        ResponseEntity response = userController.register(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void login_TestOK()  {
        when(userService.login(any())).thenReturn(UTILS_TESTCONSTANTS.getUser());
        try {
            when(userController.generateToken(UTILS_TESTCONSTANTS.getUser())).thenReturn("code");
            ResponseEntity response= userController.login(any());
            assertEquals("1",((LoginResponseDto)response.getBody()).getUserDetails().getId().toString());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getUsers_TestOK(){
        when(userService.getUsers()).thenReturn(UTILS_TESTCONSTANTS.getUserList());
        ResponseEntity<List<User>> response = userController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }
    @Test
    public void getUsers_TestBAD(){
        when(userService.getUsers()).thenReturn(new ArrayList<User>());
        ResponseEntity response = userController.getUsers();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    public void getUserById_TEstOK(){
        when(userService.getUserById(any())).thenReturn(UTILS_TESTCONSTANTS.getUser());
        ResponseEntity<User> response =userController.getUserById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void modifyUser_TestOK(){
        when(userService.modifyUser(any(),any())).thenReturn(UTILS_TESTCONSTANTS.getUser());
        ResponseEntity<User> response =userController.modifyUser(1,UTILS_TESTCONSTANTS.getUser());
        assertEquals("password", response.getBody().getPassword());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
