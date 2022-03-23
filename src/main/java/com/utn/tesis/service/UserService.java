package com.utn.tesis.service;

import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.model.User;
import com.utn.tesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws EmailExistException {

        Map<Integer,User> emailInUse = this.findUserByEmail(user.getEmail());
        User newUser=new User();
        //Check if the email is used
        if(emailInUse.containsKey(1)){
            throw  new EmailExistException("EMAIL in use.");
        }else{
            return  newUser =userRepository.save(user);
        }

    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /**
     * Check in the Data Base if the EMAIL is used.Charge a map(Key:[1 ; -1],Value:[ User ]).
     * @param email String
     * @return --A map with the User --.If the map contains the key 1:the email is used and charge the User key:-1 the email is free and charge a null User
     *
     */
    public Map<Integer,User> findUserByEmail(String email){
        List<User> usr = userRepository.findByEmail(email);
        Map<Integer,User> rtaMap= new HashMap<>();

        if(usr.size()>0) {
          rtaMap.put(1,usr.get(0));
        }else{
           rtaMap.put(-1,new User());
        }
        return rtaMap;
    }
}
