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
        boolean mailInUse=this.existsByEmail(user.getEmail());
        if(mailInUse){
            return userRepository.save(user);
        }else {
            throw new EmailExistException();
        }
    }

    /**
     * Return true if the Mail is not Use
     * @param email
     * @return boolean
     */
    public boolean existsByEmail(String email) {
        boolean rta =false;
        User user = this.getUserByEmail(email);
        if(user ==null){
            rta=true;
        }
        return rta;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /**
     * Return User with Data or NULL if the mail do not exist
     * @param mail
     * @return
     */
    public User getUserByEmail(String mail){
        User usrRta=userRepository.findByEmail(mail);
        return  usrRta;
    }
    public User login(User userLogging) {
        boolean emailExist=this.existsByEmail(userLogging.getEmail());
        User usr;
        User usrRta;
        if(!emailExist){
            usr=this.getUserByEmail(userLogging.getEmail());
            if(this.passwordEncoder.matches(userLogging.getPassword(), usr.getPassword())) {
                usrRta = usr;
            }else{
                throw  new InvalidUserOrPasswordException();
            }
        }else{
            throw  new InvalidUserOrPasswordException();
        }
        return usrRta;

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
