package com.utn.tesis.service;

import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.exception.types.PartyNotFoundException;
import com.utn.tesis.model.User;
import com.utn.tesis.repository.UserRepository;
import com.utn.tesis.UTILS_TESTCONSTANTS;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserService_Test {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserService userService;

    @Before
    public void setUp(){
        userRepository=mock(UserRepository.class);
        passwordEncoder =mock(PasswordEncoder.class);
        userService = new UserService(userRepository);
    }
    @Test
    public void registerUser_TestBad(){
        when(passwordEncoder.encode(any())).thenReturn("code");
        when(userRepository.findByEmail(any())).thenReturn(UTILS_TESTCONSTANTS.getUser());

        assertThrows(EmailExistException.class,()->userService.registerUser((UTILS_TESTCONSTANTS.getUser())));
    }
    @Test
    public void registerUser_TestOK(){
        when(passwordEncoder.encode(any())).thenReturn("code");
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(UTILS_TESTCONSTANTS.getUser());
        User response = userService.registerUser(UTILS_TESTCONSTANTS.getUser());
        assertEquals(1,response.getId());
    }
    @Test
    public void getUsers_TEstIOK(){
        when(userRepository.findAll()).thenReturn(UTILS_TESTCONSTANTS.getUserList());
        List<User> response = userService.getUsers();
        assertEquals(3,response.size());
    }
    @Test
    public void logi_TestBAD(){
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        when(userRepository.findByEmail(any())).thenReturn(UTILS_TESTCONSTANTS.getUser());
        User response = userService.login(UTILS_TESTCONSTANTS.getUser());
        assertEquals(1,response.getId());
    }
    @Test
    public void getUserById_TestOK(){
        when(userRepository.findById(any())).thenReturn(Optional.of(UTILS_TESTCONSTANTS.getUser()));
        User respose= userService.getUserById(1);
        assertEquals(respose.getId(),1);

    }

}
