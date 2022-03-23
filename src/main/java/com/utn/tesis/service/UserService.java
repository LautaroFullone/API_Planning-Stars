package com.utn.tesis.service;

import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.model.User;
import com.utn.tesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws EmailExistException {

        int emailInUse = this.emailExist(user.getEmail());
        User newUser=new User();
        //Check if the email is used
        if(emailInUse != 1){
            System.out.printf("hola");
            return    newUser =userRepository.save(user);
        }else{
            throw  new EmailExistException("EMAIL in use.");
        }

    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /**
     * Check in the Data Base if the EMAIL is used.
     * @param email String
     * @return 1 if the Mail is use un the Data Base - 0 if NOT
     *
     */
    public int emailExist(String email){
        List<User> usr = userRepository.findByEmail(email);
        int rta =-1;
        if(usr.size()>0) {
           rta = 1;
        }
        return  rta;
    }
}
