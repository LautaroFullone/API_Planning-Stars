package com.utn.tesis.service;

import com.utn.tesis.exception.types.*;
import com.utn.tesis.model.Party;
import com.utn.tesis.model.User;
import com.utn.tesis.model.UserStory;
import com.utn.tesis.model.dto.PlayerDto;
import com.utn.tesis.repository.PartyRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        party.setIsActive(true);
        party.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
       return partyRepostory.save(party);
    }

    public Party getPartyById(String idParty) {
        return this.partyRepostory.findById(idParty)
                .orElseThrow(()-> new PartyNotFoundException());
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


    public void addUserToParty(Integer idUser, String idParty) {
        //If the party or the user do not Exist throw Exception before making changes
        Party party =partyRepostory.findById(idParty).orElseThrow(()->new PartyNotFoundException());
        User user=userService.getUserById(idUser);

        //Save the party in the USER
        userService.addPartyToUser(party,idUser);
        //Add the User To the Party
        party.getUserList().add(user);
        //Save the List
        partyRepostory.save(party);

    }

    public List<PlayerDto> getPartyPlayersList(String idParty) {
        Party part = partyRepostory.findById(idParty).orElseThrow(()->new PartyNotFoundException());
        List<User> userList = part.getUserList();
        List<PlayerDto> playersList = new ArrayList<>();

        if(!userList.isEmpty())
            playersList = userList.stream().map(u -> PlayerDto.from(u)).toList();

        return playersList;
    }

    public void adduserStoryToParty(String idParty, Integer userStory) {
        Party parti=partyRepostory.findById(idParty).orElseThrow(()->new PartyNotFoundException());
        //Get US
        UserStory usCreated= userStoryService.getUserStory(userStory);
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
        Party part=partyRepostory.findById(idParty).orElseThrow(()->new PartyNotFoundException());
        return part.getUserStories();
    }

    public void deleteUs(Integer idUS) {
        userStoryService.deleteUs(idUS);
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
