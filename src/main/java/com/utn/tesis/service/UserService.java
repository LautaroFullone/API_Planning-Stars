package com.utn.tesis.service;

import com.utn.tesis.exception.types.EmailExistException;
import com.utn.tesis.exception.types.InvalidUserOrPasswordException;
import com.utn.tesis.exception.types.UserNotFoundException;
import com.utn.tesis.exception.types.idNotMatchException;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserModify;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;
   // private PartyService  partyService;
    @Autowired
    public UserService(UserRepository userRepository/*, PartyService partyService*/) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        //this.partyService = partyService;
    }




    public User registerUser(User user) throws EmailExistException {
        String encodesPass = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodesPass);
        Map<Integer,User> emailInUse = this.findUserByEmail(user.getEmail());

        //Check if the email is used
        if(emailInUse.containsKey(1))
            throw new EmailExistException();
        else
            return userRepository.save(user);
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

    public User login(User userLogging) {
        Map<Integer,User> userSeek= this.findUserByEmail(userLogging.getEmail());
        User user = new User();
        if(userSeek.containsKey(1)){
            User auxUser=userSeek.get(1);
            if(this.passwordEncoder.matches(userLogging.getPassword(), auxUser.getPassword())) {
                user = auxUser;
            }else{
                throw  new InvalidUserOrPasswordException();
            }
        }else{
            throw  new InvalidUserOrPasswordException();
        }
        return user;
    }
    public void addPartyToUser(Party party,Integer idUser){
        User usr= this.getUserById(idUser);
        //Adding Party To User
        usr.getPartiesList().add(party);
        //Saving changes
        userRepository.save(usr);

    }

    public User getUserById(Integer userId) {
        return this.userRepository.findById( userId)
                .orElseThrow(()-> new UserNotFoundException());
    }

    public List<Party> getPartyList(Integer idUser) {
        User usr =this.getUserById(idUser);
        return usr.getPartiesList();
    }


    public User modifyUser(Integer idUser, UserModify newUser) {
        User usr=this.getUserById(idUser);
        if(usr.getId() != newUser.getId()  ){
            throw new idNotMatchException();
        }else {
                    usr.setPassword(newUser.getPassword());
                    usr.setName(newUser.getName());
                    userRepository.save(usr);
        }

        return  usr;
    }
}
