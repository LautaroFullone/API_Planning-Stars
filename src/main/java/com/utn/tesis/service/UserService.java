package com.utn.tesis.service;

import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.exception.types.InvalidUserOrPasswordException;
import com.utn.tesis.exception.types.UserNotFoundException;
import com.utn.tesis.exception.types.idNotMatchException;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(User user) throws EmailExistException {
        String encodesPass = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodesPass);
        boolean mailInUse = this.existsByEmail(user.getEmail());

        if(mailInUse)
            return userRepository.save(user);
        else
            throw new EmailExistException();
    }

    public boolean existsByEmail(String email) {
        boolean rta =false;
        User user = this.getUserByEmail(email);
        if(user == null)
            rta=true;

        return rta;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getUserByEmail(String mail){
        User usrRta = userRepository.findByEmail(mail);
        return  usrRta;
    }

    public User login(User userLogging) {
        boolean emailExist=this.existsByEmail(userLogging.getEmail());
        User usr;  User usrRta;

        if(!emailExist){
            usr=this.getUserByEmail(userLogging.getEmail());
            if(this.passwordEncoder.matches(userLogging.getPassword(), usr.getPassword()))
                usrRta = usr;
            else
                throw new InvalidUserOrPasswordException();
        } else {
            throw new InvalidUserOrPasswordException();
        }
        return usrRta;
    }

    public User getUserById(Integer userId) {
        return this.userRepository.findById( userId)
                .orElseThrow(()-> new UserNotFoundException());
    }


    public User modifyUser(Integer idUser, User newUser) {
        User user = this.getUserById(idUser);
        if(user.getId() != newUser.getId() ){
            throw new idNotMatchException();
        }else {
            user.setPassword(newUser.getPassword());
            user.setName(newUser.getName());
            userRepository.save(user);
        }
        return user;
    }
}
