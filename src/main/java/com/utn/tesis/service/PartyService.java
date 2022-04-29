package com.utn.tesis.service;

import com.utn.tesis.exception.types.*;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.repository.PartyRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PartyService {

    private PartyRepostory partyRepostory;
    private  UserService userService;
    private UserStoryService  userStoryService;

    @Autowired
    public PartyService(PartyRepostory partyRepostory, UserService userService, UserStoryService userStoryService) {
        this.partyRepostory = partyRepostory;
        this.userService = userService;
        this.userStoryService = userStoryService;
    }

    public Party addParty(Party party) {
        party.setId(UUID.randomUUID().toString().toUpperCase().substring(0,6));
       return partyRepostory.save(party);
    }

    public List<Party> getParties() {
        List<Party> parties = partyRepostory.findAll();
        return  parties;
    }

    public void deleteParty(String idParty) {
        if(partyRepostory.existsById(idParty)){
            partyRepostory.deleteById(idParty);
        }else{
            throw new PartyNotFoundException();
        }
    }
    public Party existPartyByID(String idParty){
        Party party2;
        if(partyRepostory.existsById(idParty)){
            party2 =partyRepostory.getById(idParty);
        }else {
            throw new PartyNotFoundException();
        }
        return party2;
    }

    public void addUserToParty(Integer idUser, String idParty) {
        //If the party or the user do not Exist throw Exception before making changes
        Party party =this.existPartyByID(idParty);
        User user=userService.getUserById(idUser);

        //Save the party in the USER
        userService.addPartyToUser(party,idUser);
        //Add the User To the Party
        party.getUserList().add(user);
        //Save the List
        partyRepostory.save(party);

    }

    public List<User> getPartyUserList(String idParty) {
        Party part=this.existPartyByID(idParty);
        return part.getUserList();
    }

    public void adduserStoryToParty(String idParty, Integer userStory) {
        Party parti=this.existPartyByID(idParty);
        //Get US
        UserStory usCreated= userStoryService.existUSByID(userStory);
        boolean usInParty=this.existUsInParty(parti.getUserStories(),usCreated);
        if(usInParty){
            throw new UsAlreadyInThePartyException();
        }

        //add to the party
        parti.getUserStories().add(usCreated);
        //Save changes in Userstory
        userStoryService.addUserstoryToParty(parti,userStory);
        partyRepostory.save(parti);
    }
    public boolean existUsInParty(List<UserStory> userStoryList,UserStory userStory){
        boolean rta=false;
        for (UserStory us:userStoryList) {
            if(us.getId() == userStory.getId()){
                rta=true;
            }
        }
        return  rta;
    }

    public List<UserStory> getPartyUsList(String idParty) {
        Party part=this.existPartyByID(idParty);
        return part.getUserStories();
    }

    public void deleteUs(Integer idUS) {
        userStoryService.deleteUs(idUS);
    }

    public void modifyUs(Integer idUs, UserStory userStory,String idParty) {
        Party parti=this.existPartyByID(idParty);

        if (usNameRepeated(parti,userStory.getName(),idUs)){
            throw new usNameRepetedException();
        }
        if(this.isUsInTheParty(parti,idUs)) {
            userStoryService.modifyUs(idUs, userStory, parti);
        }else {
            throw new UsNotInThePartyException();
        }
    }
    public boolean usNameRepeated(Party parti,String newName,Integer idUser ){
        boolean rta =false;
        List<UserStory> userStories=parti.getUserStories();
        for (int i=0;i<userStories.size();i++){
            //me deja repetir el nombre si es en la misma us
            if(userStories.get(i).getId() == idUser){
                rta=false;
            }else{
                if (userStories.get(i).getName().equalsIgnoreCase(newName)) {
                    rta = true;
                }
            }
            }
        return rta;
    }
    public boolean isUsInTheParty(Party parti,Integer idUs){
        List<UserStory> usList=parti.getUserStories();
        boolean rta=false;
        for(int i=0;i < usList.size();i++){
            if(idUs == usList.get(i).getId()){
                rta =true;
            }
        }
        return rta;

    }

}
